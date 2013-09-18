package com.chute.android.cloudgallerytutorial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.chute.android.cloudgallery.components.GalleryViewFlipper;
import com.chute.sdk.v2.api.asset.GCAssets;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class GalleryActivity extends Activity {

  public static final String TAG = GalleryActivity.class.getSimpleName();
  private static final String ALBUM_ID = "2399241";
  private GalleryViewFlipper gallery;
  private AlbumModel album = new AlbumModel();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gallery_activity);

    gallery = (GalleryViewFlipper) findViewById(R.id.galleryId);
    album.setId(ALBUM_ID);
    GCAssets.list(getApplicationContext(), album,
        new AlbumAssetsCallback()).executeAsync();
  }

  private final class AlbumAssetsCallback implements
      HttpCallback<ListResponseModel<AssetModel>> {

    @Override
    public void onSuccess(ListResponseModel<AssetModel> responseData) {
      gallery.setAssetCollection(responseData.getData());

    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      Toast.makeText(getApplicationContext(), R.string.http_error,
          Toast.LENGTH_SHORT).show();

    }
  }
}