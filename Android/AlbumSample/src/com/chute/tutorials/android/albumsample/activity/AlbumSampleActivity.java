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
package com.chute.tutorials.android.albumsample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.chute.tutorials.android.albumsample.R;
import com.chute.tutorials.android.albumsample.util.Constants;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AlbumSampleActivity extends Activity {

  public static final String TAG = AlbumSampleActivity.class.getSimpleName();
  private Button createAlbum;
  private Button getAlbumList;
  private Button getAlbum;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_album_tutorial);

    createAlbum = (Button) findViewById(R.id.buttonCreateAlbum);
    getAlbumList = (Button) findViewById(R.id.buttonAlbumList);
    getAlbum = (Button) findViewById(R.id.buttonAlbum);

    createAlbum.setOnClickListener(new CreateAlbumClickListener());
    getAlbum.setOnClickListener(new GetAlbumClickListener());
    getAlbumList.setOnClickListener(new GetAlbumListClickListener());

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  /* CREATE */
  private final class CreateAlbumClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      AlbumModel album = new AlbumModel();
      album.setName(Constants.TEST_ALBUM_NAME);
      GCAlbums.create(getApplicationContext(), album, new AlbumCreateCallback())
          .executeAsync();
    }

  }

  /* GET */
  private final class GetAlbumClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      AlbumModel album = new AlbumModel();
      album.setId(Constants.TEST_ALBUM_ID);
      GCAlbums.get(getApplicationContext(), album, new AlbumGetCallback()).executeAsync();
    }

  }

  /* LIST */
  private final class GetAlbumListClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      Intent intent = new Intent(getApplicationContext(),
          AlbumListActivity.class);
      startActivity(intent);
    }

  }

  private final class AlbumCreateCallback implements
      HttpCallback<ResponseModel<AlbumModel>> {

    @Override
    public void onSuccess(ResponseModel<AlbumModel> responseData) {
      Toast.makeText(getApplicationContext(),
          getResources().getString(R.string.txt_album_created),
          Toast.LENGTH_SHORT).show();
      ALog.d("Created Album: " + responseData.getData().toString());

    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      Toast.makeText(
          getApplicationContext(),
          getResources().getString(R.string.txt_error_occurred) + " "
              + responseCode.getStatusMessage(),
          Toast.LENGTH_SHORT).show();
      Log.e(TAG, getResources().getString(R.string.txt_error_occurred)
          + " " + responseCode.getStatusMessage());
    }

  }

  private final class AlbumGetCallback implements
      HttpCallback<ResponseModel<AlbumModel>> {

    @Override
    public void onSuccess(ResponseModel<AlbumModel> responseData) {
      Toast.makeText(getApplicationContext(),
          R.string.toast_album_delivered,
          Toast.LENGTH_SHORT).show();
      ALog.d("Album: " + responseData.getData().toString());
    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      Toast.makeText(
          getApplicationContext(),
          getResources().getString(R.string.txt_error_occurred) + " "
              + responseCode.getStatusMessage(),
          Toast.LENGTH_SHORT).show();
      Log.e(TAG, getResources().getString(R.string.txt_error_occurred)
          + " " + responseCode.getStatusMessage());
    }

  }

}
