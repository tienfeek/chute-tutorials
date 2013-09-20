package com.chute.android.albumtutorial.ui.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.chute.android.albumtutorial.R;
import com.chute.android.gallerylisting.app.GalleryListingActivity;
import com.chute.android.imagegrid.intent.ImageGridIntentWrapper;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AlbumListActivity extends GalleryListingActivity {

  public static final String TAG = AlbumListActivity.class.getSimpleName();
  final CharSequence[] items = { "Update", "Delete", "Import", "View" };

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

  public void showDialog(final AlbumModel album) {
    AlertDialog.Builder builder = new AlertDialog.Builder(AlbumListActivity.this);
    builder.setItems(items, new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int which) {
        switch (which) {
        case 0:
          GCAlbums.update(getApplicationContext(), album, new AlbumUpdateCallback())
              .executeAsync();
          break;
        case 1:
          GCAlbums.delete(getApplicationContext(), album, new AlbumDeleteCallback())
              .executeAsync();
          break;
        case 2:
          ArrayList<String> urls = new ArrayList<String>();
          String url = "http://goo.gl/M0aXim";
          urls.add(url);
          GCAlbums.imports(getApplicationContext(), album, urls,
              new AlbumImportCallback()).executeAsync();
          break;
        case 3:
          ImageGridIntentWrapper wrapper = new ImageGridIntentWrapper(
              AlbumListActivity.this);
          wrapper.setAlbumId(album.getId());
          wrapper.startActivity(AlbumListActivity.this);
          break;
        }
      }
    });
    builder.create();
    builder.show();
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

  private final class AlbumUpdateCallback implements
      HttpCallback<ResponseModel<AlbumModel>> {

    @Override
    public void onSuccess(ResponseModel<AlbumModel> responseData) {
      Toast.makeText(getApplicationContext(),
          getResources().getString(R.string.txt_album_updated),
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

  private final class AlbumImportCallback implements
      HttpCallback<ListResponseModel<AssetModel>> {

    @Override
    public void onSuccess(ListResponseModel<AssetModel> responseData) {
      Toast.makeText(getApplicationContext(),
          "Images imported",
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
