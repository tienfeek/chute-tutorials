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
package com.chute.android.albumtutorial.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.util.Constants;
import com.chute.android.gallerylisting.activity.GalleryListingActivity;
import com.chute.android.gallerylisting.adapter.GalleryListingAdapter;
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
          urls.add(Constants.IMAGE_IMPORT_URL);
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
      refreshAlbumList();
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
      refreshAlbumList();
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
          R.string.toast_images_imported,
          Toast.LENGTH_SHORT).show();
      ((GalleryListingAdapter) listView.getAdapter()).notifyDataSetChanged();

    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      Toast.makeText(getApplicationContext(),
          getResources().getString(R.string.txt_http_error),
          Toast.LENGTH_SHORT).show();

    }

  }

  private void refreshAlbumList() {
    GCAlbums.list(getApplicationContext(), new AlbumListCallback()).executeAsync();

  }

  private final class AlbumListCallback implements
      HttpCallback<ListResponseModel<AlbumModel>> {

    @Override
    public void onHttpError(ResponseStatus status) {
      ALog.d("Unable to get album list: " + status.getStatusCode() + " "
          + status.getStatusMessage());

    }

    @Override
    public void onSuccess(ListResponseModel<AlbumModel> responseData) {
      listView.setAdapter(new GalleryListingAdapter(AlbumListActivity.this, responseData
          .getData()));
      ((GalleryListingAdapter) listView.getAdapter()).notifyDataSetChanged();

    }

  }

}
