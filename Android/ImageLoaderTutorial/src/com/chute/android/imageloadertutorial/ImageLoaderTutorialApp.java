package com.chute.android.imageloadertutorial;

import com.chute.android.imageloadertutorial.R;
import com.dg.libs.rest.authentication.TokenAuthenticationProvider;
import com.dg.libs.rest.client.BaseRestClient;

import darko.imagedownloader.ImageLoader;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

public class ImageLoaderTutorialApp extends Application {

  public static final String TAG = ImageLoaderTutorialApp.class
      .getSimpleName();

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
    TokenAuthenticationProvider.init(getApplicationContext());
    TokenAuthenticationProvider provider = TokenAuthenticationProvider
        .getInstance();
    // Test token
    provider.setToken("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");
    BaseRestClient.setDefaultAuthenticationProvider(provider);
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
