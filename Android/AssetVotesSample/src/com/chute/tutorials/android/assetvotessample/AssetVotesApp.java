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
package com.chute.tutorials.android.assetvotessample;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.araneaapps.android.libs.logger.ALog;
import com.araneaapps.android.libs.logger.ALog.DebugLevel;
import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;
import com.chute.tutorials.android.assetvotessample.util.AssetVotesPreferences;

import darko.imagedownloader.ImageLoader;

public class AssetVotesApp extends Application {

  public static final String TAG = AssetVotesApp.class.getSimpleName();

  /* Test Credentials */
  final String APP_ID = "4f3c39ff38ecef0c89000003";
  final String APP_SECRET = "c9a8cb57c52f49384ab6117c4f6483a1a5c5a14c4a50d4cef276a9a13286efc9";
  final String TOKEN = "f7f1a31c46f95f4085956ae146aa0f3eec1874a9d17ec07de5e22d7c7340da0e";

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
    ALog.setDebugTag("AssetVotesSample");
    ALog.setDebugLevel(DebugLevel.ALL);
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
