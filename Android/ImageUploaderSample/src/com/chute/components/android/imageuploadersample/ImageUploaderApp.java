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
package com.chute.components.android.imageuploadersample;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;

import darko.imagedownloader.ImageLoader;

public class ImageUploaderApp extends Application {

  public static final String TAG = ImageUploaderApp.class.getSimpleName();
  private ImageLoader mImageLoader;
  private static final String TOKEN = "2459ce0016bdbacd8c3eaa23333b183f0e9d6aa8322ad63fa06eed3d40162844";
  private static final String CLIENT_ID = "4f3c39ff38ecef0c89000003";
  private static final String CLIENT_SECRET = "c9a8cb57c52f49384ab6117c4f6483a1a5c5a14c4a50d4cef276a9a13286efc9";

  @Override
  public void onCreate() {
    super.onCreate();
    mImageLoader = createImageLoader(this);
    Chute.init(this, new AuthConstants(CLIENT_ID, CLIENT_SECRET));
  }

  private static ImageLoader createImageLoader(Context context) {
    ImageLoader imageLoader = new ImageLoader(context, R.drawable.ic_launcher);
    imageLoader.setDefaultBitmapSize((int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 75, context
            .getResources().getDisplayMetrics()));
    return imageLoader;
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
