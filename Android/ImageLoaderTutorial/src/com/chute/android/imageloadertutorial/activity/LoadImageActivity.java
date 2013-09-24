package com.chute.android.imageloadertutorial.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.chute.android.imageloadertutorial.R;
import com.chute.android.imageloadertutorial.intent.LoadImageActivityIntentWrapper;
import com.chute.android.imageloadertutorial.util.Constants;
import com.chute.android.imageloadertutorial.util.CursorUtil;

import darko.imagedownloader.ImageLoader;

public class LoadImageActivity extends Activity {

  public static final String TAG = LoadImageActivity.class.getSimpleName();

  private ImageView imageView;
  private ImageLoader loader;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load_image);

    loader = ImageLoader.getLoader(LoadImageActivity.this);

    imageView = (ImageView) findViewById(R.id.imageView);
    LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
        getIntent());

    switch (wrapper.getFilterType()) {
    case LoadImageActivityIntentWrapper.TYPE_SDCARD:
      Uri lastPhotoFromCameraPhotos = CursorUtil
          .getLastPhotoFromAllPhotos(getApplicationContext());
      loader.displayImage(lastPhotoFromCameraPhotos.toString(), imageView,
          null);
      break;
    case LoadImageActivityIntentWrapper.TYPE_URL:
      loader.displayImage(Constants.URL, imageView, null);
      break;
    case LoadImageActivityIntentWrapper.TYPE_CHUTE_URL:
      loader.displayImage(Constants.CHUTE_URL, imageView, null);
      break;
    default:
      break;
    }

  }

}
