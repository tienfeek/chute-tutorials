package com.chute.android.albumtutorial.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.util.Constants;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AlbumTutorialActivity extends Activity {

  private static final String TAG = AlbumTutorialActivity.class.getSimpleName();
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
