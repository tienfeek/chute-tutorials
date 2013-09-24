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
package com.chute.android.imageloadertutorial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.chute.android.imageloadertutorial.R;
import com.chute.android.imageloadertutorial.intent.LoadImageActivityIntentWrapper;

public class ImageLoaderTutorialActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_loader_tutorial);

    findViewById(R.id.buttonSdcard)
        .setOnClickListener(new OnButtonSdcardClicked());

    findViewById(R.id.buttonUrl)
        .setOnClickListener(new OnButtonUrlClicked());

    findViewById(R.id.buttonChutePhotoUrl)
        .setOnClickListener(new OnButtonChutePhotoUrlClicked());
  }

  private final class OnButtonSdcardClicked implements OnClickListener {

    @Override
    public void onClick(View v) {
      LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
          ImageLoaderTutorialActivity.this);
      wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_SDCARD);
      wrapper.startActivity(ImageLoaderTutorialActivity.this);
    }

  }

  private final class OnButtonUrlClicked implements OnClickListener {

    @Override
    public void onClick(View v) {
      LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
          ImageLoaderTutorialActivity.this);
      wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_URL);
      wrapper.startActivity(ImageLoaderTutorialActivity.this);
    }

  }

  private final class OnButtonChutePhotoUrlClicked implements OnClickListener {

    @Override
    public void onClick(View v) {
      LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
          ImageLoaderTutorialActivity.this);
      wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_CHUTE_URL);
      wrapper.startActivity(ImageLoaderTutorialActivity.this);
    }

  }
}