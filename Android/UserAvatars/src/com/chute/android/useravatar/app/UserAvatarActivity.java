package com.chute.android.useravatar.app;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.android.photopickerplus.util.intent.GridActivityIntentWrapper;
import com.chute.android.photopickerplus.util.intent.PhotoPickerPlusIntentWrapper;
import com.chute.android.useravatar.R;
import com.chute.android.useravatar.util.AppUtil;
import com.chute.android.useravatar.util.intent.CropImageIntentWrapper;
import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.asset.GCAssets;
import com.chute.sdk.v2.api.authentication.AuthConstants;
import com.chute.sdk.v2.api.authentication.AuthenticationFactory;
import com.chute.sdk.v2.api.authentication.TokenAuthenticationProvider;
import com.chute.sdk.v2.api.upload.UploadProgressListener;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.chute.sdk.v2.utils.Utils;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import darko.imagedownloader.FileCache;
import darko.imagedownloader.ImageLoader;

public class UserAvatarActivity extends Activity {

  private static final String CHUTE_TEST_ID = "2399241";

  private static final String TAG = UserAvatarActivity.class.getSimpleName();

  private static final int REQUEST_CROP_IMAGE = 42;
  public static int REQUEST_CODE_PP = 1232;

  private ImageView thumb;

  private TextView path;

  private ProgressBar pb;

  private File tempFileForCroppedImage;

  private AlbumModel album;

  private ImageLoader loader;

  private int width = 200;
  private int height = 200;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Button choosePhoto = (Button) findViewById(R.id.btnChoosePhoto);
    choosePhoto.setOnClickListener(new ChoosePhotoClickListener());

    loader = ImageLoader.getLoader(this);

    thumb = (ImageView) findViewById(R.id.imageViewThumb);
    path = (TextView) findViewById(R.id.textViewPath);
    pb = (ProgressBar) findViewById(R.id.progressBarUpload);

    album = new AlbumModel();
    album.setId(CHUTE_TEST_ID); // Test ID for a public chute called
    // Upload Test
  }

  private class ChoosePhotoClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      final PhotoPickerPlusIntentWrapper wrapper = new PhotoPickerPlusIntentWrapper(
          UserAvatarActivity.this);
      wrapper.setAlbumId(CHUTE_TEST_ID);
      wrapper.startActivityForResult(UserAvatarActivity.this,
          REQUEST_CODE_PP);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK) {
      return;
    }
    if (requestCode == REQUEST_CODE_PP) {
      final GridActivityIntentWrapper wrapper = new GridActivityIntentWrapper(
          data);

      Uri uri = Uri.parse(wrapper.getMediaCollection().get(0).getUrl());
      if (uri.getScheme().contentEquals("http")) {
        new DownloadBitmapTask(uri.toString()).execute();
      } else {
        tempFileForCroppedImage = new FileCache(getApplicationContext())
            .getFile(uri.getPath());
        tempFileForCroppedImage.deleteOnExit();
        Log.d(TAG, tempFileForCroppedImage.getPath());
        CropImageIntentWrapper cropWrapper = new CropImageIntentWrapper(
            UserAvatarActivity.this);
        cropWrapper.setOutputX(width);
        cropWrapper.setOutputY(height);
        cropWrapper.setAspectX(width);
        cropWrapper.setAspectY(height);
        cropWrapper.setScale(true);
        cropWrapper.setNoFaceDetection(true);
        cropWrapper.setUri(Uri.fromFile(new File(uri.getPath())));
        cropWrapper.setOutput(Uri.fromFile(tempFileForCroppedImage));
        cropWrapper.startActivityForResult(UserAvatarActivity.this,
            REQUEST_CROP_IMAGE);

        return;
      }
    }
    if (requestCode == REQUEST_CROP_IMAGE) {
      String imagePath = data.getStringExtra("imagePath");
      Bitmap croppedImage = data.getParcelableExtra("image");
      thumb.setImageBitmap(croppedImage);
      path.setText(data.getData().toString());
      uploadPhoto(data.getData().getPath());
    }
  }

  private class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

    private String string;

    public DownloadBitmapTask(String string) {
      this.string = string;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
      return ImageLoader.getLoader(getApplicationContext())
          .downloadBitmap(string);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
      super.onPostExecute(result);
      File file = AppUtil.getFilefromBitmap(getApplicationContext(),
          result);
      tempFileForCroppedImage = new FileCache(getApplicationContext())
          .getFile(file.getAbsolutePath());
      tempFileForCroppedImage.deleteOnExit();
      Log.d(TAG, tempFileForCroppedImage.getPath());

      CropImageIntentWrapper cropWrapper = new CropImageIntentWrapper(
          UserAvatarActivity.this);
      cropWrapper.setOutputX(width);
      cropWrapper.setOutputY(height);
      cropWrapper.setAspectX(width);
      cropWrapper.setAspectY(height);
      cropWrapper.setScale(true);
      cropWrapper.setNoFaceDetection(true);
      cropWrapper.setUri(Uri.fromFile(new File(file.getAbsolutePath())));
      cropWrapper.setOutput(Uri.fromFile(tempFileForCroppedImage));
      cropWrapper.startActivityForResult(UserAvatarActivity.this,
          REQUEST_CROP_IMAGE);
      return;

    }

  }

  private void uploadPhoto(String imagePath) {
    GCAssets.uploadOneStep(getApplicationContext(), new FileUploadListener(), album,
        imagePath, new AssetUploadCallback()).executeAsync();
  }

  private final class AssetUploadCallback implements
      HttpCallback<ListResponseModel<AssetModel>> {

    @Override
    public void onSuccess(ListResponseModel<AssetModel> responseData) {
      Log.d(TAG, responseData.toString());
      final String url = responseData.getData().get(0).getUrl();
      UserAvatarActivity.this.runOnUiThread(new Runnable() {

        @Override
        public void run() {
          loader.displayImage(
              Utils.getCustomSizePhotoURL(url, 75, 75), thumb, null);
        }
      });
    }

    @Override
    public void onHttpError(ResponseStatus status) {
      ALog.e(TAG, "Upload callback Http Error " + status.getStatusMessage()
          + " Code " + status.getStatusCode());

    }

  }

  private final class FileUploadListener implements
      UploadProgressListener {

    @Override
    public void onProgress(long total, long uploaded) {
      int percent = (int) ((uploaded * 100) / total);
      Log.d(TAG, "Current progress Text" + percent + "%");
      pb.setProgress(percent);
    }

    @Override
    public void onUploadFinished(File file) {
      Log.d(TAG, "Upload finished");

    }

    @Override
    public void onUploadStarted(File file) {
      Log.d(TAG, "Upload started");
      pb.setMax(100);
      pb.setProgress(0);

    }
  }

}