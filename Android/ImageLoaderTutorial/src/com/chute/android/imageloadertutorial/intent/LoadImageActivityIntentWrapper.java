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
package com.chute.android.imageloadertutorial.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chute.android.imageloadertutorial.activity.LoadImageActivity;

public class LoadImageActivityIntentWrapper {

  public static final String TAG = LoadImageActivityIntentWrapper.class
      .getSimpleName();

  private static final String EXTRA_KEY_PHOTO = "extraKeyPhoto";

  public static final int TYPE_SDCARD = 0;
  public static final int TYPE_URL = 1;
  public static final int TYPE_CHUTE_URL = 2;

  private final Intent intent;

  public LoadImageActivityIntentWrapper(Intent intent) {
    super();
    this.intent = intent;
  }

  public LoadImageActivityIntentWrapper(Context packageContext, Class<?> cls) {
    super();
    intent = new Intent(packageContext, cls);
  }

  public LoadImageActivityIntentWrapper(Context packageContext) {
    super();
    intent = new Intent(packageContext, LoadImageActivity.class);
  }

  public Intent getIntent() {
    return intent;
  }

  public void setFilterType(int type) {
    intent.putExtra(EXTRA_KEY_PHOTO, type);
  }

  public int getFilterType() {
    return intent.getExtras().getInt(EXTRA_KEY_PHOTO);
  }

  public void startActivity(Activity context) {
    context.startActivity(intent);
  }
}
