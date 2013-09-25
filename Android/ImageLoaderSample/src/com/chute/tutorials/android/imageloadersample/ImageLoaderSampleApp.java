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
package com.chute.tutorials.android.imageloadersample;

import com.dg.libs.rest.authentication.TokenAuthenticationProvider;
import com.dg.libs.rest.client.BaseRestClient;

import darko.imagedownloader.ImageLoader;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

public class ImageLoaderSampleApp extends Application {

  public static final String TAG = ImageLoaderSampleApp.class
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
