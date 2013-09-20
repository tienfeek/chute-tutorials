package com.chute.android.albumtutorial;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.util.Constants;
import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;

import darko.imagedownloader.ImageLoader;

public class AlbumTutorialApp extends Application {

  public static final String TAG = AlbumTutorialApp.class.getSimpleName();

  private static ImageLoader createImageLoader(Context context) {
    ImageLoader imageLoader = new ImageLoader(context,
        R.drawable.placeholder);
    imageLoader.setDefaultBitmapSize((int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 75, context.getResources()
            .getDisplayMetrics()));
    return imageLoader;
  }

  private ImageLoader mImageLoader;

  @Override
  public void onCreate() {
    super.onCreate();
    Chute.init(getApplicationContext(), new AuthConstants(Constants.APP_ID,
        Constants.APP_SECRET), Constants.TOKEN2);
    mImageLoader = createImageLoader(this);
  }

  @Override
  public Object getSystemService(String name) {
    if (ImageLoader.IMAGE_LOADER_SERVICE.equals(name)) {
      return mImageLoader;
    } else {
      return super.getSystemService(name);
    }
  }

}
