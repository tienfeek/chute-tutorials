package com.chute.android.assetvotestutorial.app;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.chute.android.assetvotestutorial.R;
import com.chute.android.assetvotestutorial.util.AssetVotesPreferences;
import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;

import darko.imagedownloader.ImageLoader;

public class AssetVotesApp extends Application {

  public static final String TAG = AssetVotesApp.class.getSimpleName();
  
  /* Test Credentials */
  final String APP_ID = "4f3c39ff38ecef0c89000003";
  final String APP_SECRET = "c9a8cb57c52f49384ab6117c4f6483a1a5c5a14c4a50d4cef276a9a13286efc9";
  final String TOKEN = "f7f1a31c46f95f4085956ae146aa0f3eec1874a9d17ec07de5e22d7c7340da0e";

  private static ImageLoader createImageLoader(Context context) {
    ImageLoader imageLoader = new ImageLoader(context,
        R.drawable.placeholder_image_small);
    imageLoader.setDefaultBitmapSize((int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 75, context.getResources()
            .getDisplayMetrics()));
    return imageLoader;
  }

  private ImageLoader mImageLoader;

  @Override
  public void onCreate() {
    super.onCreate();
    Chute.init(getApplicationContext(), new AuthConstants(APP_ID, APP_SECRET), TOKEN);
    mImageLoader = createImageLoader(this);
    AssetVotesPreferences.init(getApplicationContext());
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
