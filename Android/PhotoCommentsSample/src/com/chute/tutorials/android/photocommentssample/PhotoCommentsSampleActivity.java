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
package com.chute.tutorials.android.photocommentssample;

import com.chute.components.android.photocomments.intent.MainActivityIntentWrapper;
import com.chute.components.android.photocomments.intent.PhotoCommentsActivityIntentWrapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PhotoCommentsSampleActivity extends Activity implements
    OnClickListener {

  public static final String TAG = PhotoCommentsSampleActivity.class
      .getSimpleName();
  private static final int ACTIVITY_FOR_RESULT_KEY = 113;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button startComments = (Button) findViewById(R.id.btnStartComments);
    startComments.setOnClickListener(this);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }


  @Override
  public void onClick(View v) {
    PhotoCommentsActivityIntentWrapper wrapper = new PhotoCommentsActivityIntentWrapper(
        this);
    wrapper.setAlbumId("2399241"); // Replace with album id
    wrapper.setAssetId("338549979"); // Replace with asset id
    wrapper.setAlbumName("Reykjavik"); // Name of the album
    wrapper.startActivityForResult(this, ACTIVITY_FOR_RESULT_KEY);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode != ACTIVITY_FOR_RESULT_KEY
        || resultCode != RESULT_OK) {
      return;
    }
    MainActivityIntentWrapper wrapper = new MainActivityIntentWrapper(data);
    if (wrapper.getExtraComments() > 0) {
      Toast.makeText(
          getApplicationContext(),
          wrapper.getExtraComments()
              + getApplicationContext().getResources().getString(
                  R.string.comments_added),
          Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(
          getApplicationContext(),
          getApplicationContext().getResources().getString(
              R.string.no_comments_added), Toast.LENGTH_SHORT)
          .show();
    }
  }

}