/**
 * The MIT License (MIT)

Copyright (c) 2013 Chute

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.chute.components.android.imageuploadersample.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.components.android.imageuploadersample.R;
import com.chute.components.android.imageuploadersample.util.FileUtil;
import com.chute.sdk.v2.api.asset.GCAssets;
import com.chute.sdk.v2.api.upload.UploadProgressListener;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.LocalAssetModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import darko.imagedownloader.ImageLoader;

public class UploadActivity extends Activity {

  public static final String TAG = UploadActivity.class.getSimpleName();
  private ImageView imageThumb;
  private ProgressBar pb;
  private ImageLoader imageLoader;
  private TextView fileText;
  private String assetName;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload);
    imageLoader = ImageLoader.getLoader(getApplicationContext());
    final Button startUpload = (Button) findViewById(R.id.buttonStartUpload);
    startUpload.setOnClickListener(new OnUploadClickListener());
    fileText = (TextView) findViewById(R.id.textView);

    pb = (ProgressBar) findViewById(R.id.progressBar);

    imageThumb = (ImageView) findViewById(R.id.imageView);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  private final class OnUploadClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      LocalAssetModel asset = new LocalAssetModel();
      try {
        asset.setFile(FileUtil.copyAsset(getApplicationContext(), "android.jpg"));
      } catch (IOException e) {
        Log.w(TAG, "", e);
      }

      final ArrayList<LocalAssetModel> assetCollection = new ArrayList<LocalAssetModel>();
      assetCollection.add(asset);

      final AlbumModel chuteModel = new AlbumModel();
      chuteModel.setId("2400518");
      final ArrayList<AlbumModel> chuteCollection = new ArrayList<AlbumModel>();
      chuteCollection.add(chuteModel);

      assetName = asset.getFile().getName();
      uploadOneStep(asset.getFile().getAbsolutePath(), chuteModel);

    }
  }

  public void uploadOneStep(String filePath, AlbumModel album) {
    GCAssets.uploadOneStep(getApplicationContext(),
        new GCUploadProgressListenerImplementation(), album, filePath,
        new UploadOneStepCallback()).executeAsync();
  }

  private final class UploadOneStepCallback implements
      HttpCallback<ListResponseModel<AssetModel>> {

    @Override
    public void onHttpError(ResponseStatus status) {
      ALog.d("response status = " + status.getStatusCode() + " "
          + status.getStatusMessage());

    }

    @Override
    public void onSuccess(ListResponseModel<AssetModel> responseData) {
      ALog.d("success = " + responseData.toString());
      if (responseData.getData().get(0).getThumbnail() != null) {
        String assetPath = responseData.getData().get(0).getThumbnail();
        imageLoader.displayImage(assetPath, imageThumb, null);
      }
      fileText.setText(assetName);
    }

  }

  private final class GCUploadProgressListenerImplementation implements
      UploadProgressListener {

    private static final int DELAY = 400;

    private Timer t;
    private long total;
    private long uploaded;

    class ProgressTask extends TimerTask {

      @Override
      public void run() {
        if (total != 0) {
          int percent = (int) ((uploaded * 100) / total);
          ALog.e(TAG, "Current progress Text" + percent + "%");
          pb.setProgress(percent);
        }
      }
    }

    @Override
    public void onProgress(long total, long uploaded) {
      this.total = total;
      this.uploaded = uploaded;
      Log.d(TAG, "Content Size " + String.valueOf(total));
      Log.d(TAG, "Current progress " + String.valueOf(uploaded));
    }

    @Override
    public void onUploadStarted(final File file) {
      t = new Timer();
      t.schedule(new ProgressTask(), DELAY, DELAY);
      pb.setMax(100);
      UploadActivity.this.runOnUiThread(new Runnable() {

        @Override
        public void run() {
          Bitmap thumbnail = BitmapFactory.decodeFile(file.getAbsolutePath());
          imageThumb.setImageBitmap(thumbnail);
          fileText.setText(file.getAbsolutePath());
        }
      });
    }

    @Override
    public void onUploadFinished(File file) {
      t.cancel();
    }

  }

}
