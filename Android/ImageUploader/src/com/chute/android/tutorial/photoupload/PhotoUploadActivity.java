package com.chute.android.tutorial.photoupload;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chute.sdk.v2.api.asset.GCAssets;
import com.chute.sdk.v2.api.upload.UploadProgressListener;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.LocalAssetModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class PhotoUploadActivity extends Activity {

  private static final String TAG = PhotoUploadActivity.class.getSimpleName();
  private ImageView imageThumb;
  private ProgressBar pb;
  private TextView fileText;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    final Button startUpload = (Button) findViewById(R.id.buttonStartUpload);
    startUpload.setOnClickListener(new OnUploadClickListener());
    fileText = (TextView) findViewById(R.id.textView);

    pb = (ProgressBar) findViewById(R.id.progressBar);

    imageThumb = (ImageView) findViewById(R.id.imageView);
  }

  private final class OnUploadClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      LocalAssetModel asset = new LocalAssetModel();
      try {
        asset.setFile(FileUtil.copyAsset(getApplicationContext(),
            "droid4.jpg"));
      } catch (IOException e) {
        Log.w(TAG, "", e);
      }

      String filePath = asset.getFile().getAbsolutePath();
      AlbumModel album = new AlbumModel();
      album.setId("");

      GCAssets.uploadOneStep(getApplicationContext(),
          new GCUploadProgressListenerImplementation(), album, filePath,
          new UploadCallback()).executeAsync();

    }
  }

  private final class UploadCallback implements
      HttpCallback<ListResponseModel<AssetModel>> {

    @Override
    public void onHttpError(ResponseStatus status) {
      Log.d(
          TAG,
          "Upload callback error: " + status.getStatusCode() + " "
              + status.getStatusMessage());
      Toast.makeText(getApplicationContext(), R.string.http_exception,
          Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccess(ListResponseModel<AssetModel> responseData) {
      Log.e(TAG, responseData.getData().toString());

    }

  }

  private final class GCUploadProgressListenerImplementation implements
      UploadProgressListener {

    private static final int DELAY = 1000;

    private Timer t;
    private long total;
    private long uploaded;

    class ProgressTask extends TimerTask {

      @Override
      public void run() {
        int percent = (int) ((uploaded * 100) / total);
        Log.e(TAG, "Current progress Text" + percent + "%");
        pb.setProgress(percent);
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
      Log.d(TAG, "Upload started");
      t = new Timer();
      t.schedule(new ProgressTask(), DELAY, DELAY);
      pb.setMax(100);
      PhotoUploadActivity.this.runOnUiThread(new Runnable() {

        @Override
        public void run() {
          // imageThumb.setImageBitmap(thumbnail);
          // fileText.setText(file.getAbsolutePath());
        }
      });
    }

    @Override
    public void onUploadFinished(File file) {
      Log.d(TAG, "Upload finished");
      t.cancel();
    }

  }

}