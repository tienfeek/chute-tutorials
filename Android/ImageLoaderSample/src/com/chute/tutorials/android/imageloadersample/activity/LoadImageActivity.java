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
package com.chute.tutorials.android.imageloadersample.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.chute.tutorials.android.imageloadersample.R;
import com.chute.tutorials.android.imageloadersample.intent.LoadImageActivityIntentWrapper;
import com.chute.tutorials.android.imageloadersample.util.Constants;
import com.chute.tutorials.android.imageloadersample.util.CursorUtil;

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
