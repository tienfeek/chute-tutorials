package com.chute.android.useravatar.app;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.chute.android.photopickerplus.config.PhotoPicker;
import com.chute.android.photopickerplus.config.PhotoPickerConfiguration;
import com.chute.android.photopickerplus.util.PhotoPickerPreferenceUtil;
import com.chute.android.useravatar.R;
import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;
import com.chute.sdk.v2.utils.PreferenceUtil;

import darko.imagedownloader.ImageLoader;

public class UserAvatarApp extends Application {

  @SuppressWarnings("unused")
  private static final String TAG = UserAvatarApp.class.getSimpleName();
  final String APP_ID = "4f3c39ff38ecef0c89000003";
  final String APP_SECRET = "c9a8cb57c52f49384ab6117c4f6483a1a5c5a14c4a50d4cef276a9a13286efc9";
  final String TOKEN = "f7f1a31c46f95f4085956ae146aa0f3eec1874a9d17ec07de5e22d7c7340da0e";

  private static ImageLoader createImageLoader(Context context) {
    ImageLoader imageLoader = new ImageLoader(context, R.drawable.placeholder_image_small);
    imageLoader.setDefaultBitmapSize((int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        75, context.getResources().getDisplayMetrics()));
    return imageLoader;
  }

  private ImageLoader mImageLoader;

  @Override
  public void onCreate() {
    super.onCreate();
    mImageLoader = createImageLoader(this);
    PreferenceUtil.init(getApplicationContext());
    PhotoPickerPreferenceUtil.init(getApplicationContext());
    Chute.init(this, new AuthConstants(APP_ID, APP_SECRET), TOKEN);
    PhotoPickerConfiguration config = new PhotoPickerConfiguration.Builder(
        getApplicationContext())
        .build();
    PhotoPicker.getInstance().init(config);
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
