package com.chute.android.albumtutorial.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.chute.android.albumtutorial.R;
import com.chute.android.gallerylisting.app.GalleryListingActivity;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AlbumListActivity extends GalleryListingActivity {

  public static final String TAG = AlbumListActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    listView.setOnItemClickListener(new AlbumsClickListener());

  }

  private final class AlbumsClickListener implements OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
        long id) {
      AlbumModel model = (AlbumModel) listView.getAdapter().getItem(
          position);
      showDialog(model);
    }

  }

  public void showDialog(final AlbumModel model) {
    final Dialog dialog = new Dialog(this);
    // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.album_list_dialog);
    dialog.setTitle(R.string.delete_this_album_);
    Button delete = (Button) dialog.findViewById(R.id.buttonYes);
    delete.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        GCAlbums.delete(getApplicationContext(), model,
            new AlbumDeleteCallback()).executeAsync();
        dialog.dismiss();
      }
    });
    Button no = (Button) dialog.findViewById(R.id.buttonNo);
    no.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });
    dialog.show();
  }

  private final class AlbumDeleteCallback implements
      HttpCallback<ResponseModel<AlbumModel>> {

    @Override
    public void onSuccess(ResponseModel<AlbumModel> responseData) {
      Toast.makeText(getApplicationContext(),
          getResources().getString(R.string.txt_album_deleted),
          Toast.LENGTH_SHORT).show();
      // TODO refresh album list

    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      Toast.makeText(getApplicationContext(),
          getResources().getString(R.string.txt_http_error),
          Toast.LENGTH_SHORT).show();

    }

  }

}
