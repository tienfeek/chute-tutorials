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
package com.chute.android.assetvotestutorial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.android.assetvotestutorial.R;
import com.chute.android.assetvotestutorial.util.AssetVotesPreferences;
import com.chute.android.assetvotestutorial.util.Constants;
import com.chute.android.cloudgallery.components.GalleryViewFlipper;
import com.chute.android.cloudgallery.components.GalleryViewFlipper.GalleryCallback;
import com.chute.android.cloudgallery.components.GalleryViewFlipper.PhotoChangeErrorType;
import com.chute.android.cloudgallery.zoom.PinchZoomListener.GestureEvent;
import com.chute.sdk.v2.api.asset.GCAssets;
import com.chute.sdk.v2.api.hearts.GCHearts;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.HeartModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AssetVotesTutorialActivity extends Activity {

  @SuppressWarnings("unused")
  private static final String TAG = AssetVotesTutorialActivity.class
      .getSimpleName();
  private ImageButton heart;
  private GalleryViewFlipper gallery;
  private AlbumModel album = new AlbumModel();
  private boolean isHearted;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_asset_vote);

    gallery = (GalleryViewFlipper) findViewById(R.id.galleryId);
    heart = (ImageButton) findViewById(R.id.imageButtonHeart);
    heart.setOnClickListener(new HeartClickListener());
    gallery.setGalleryCallback(new NewGalleryCallback());

    album.setId(Constants.ALBUM_ID);
    GCAssets.list(getApplicationContext(), album,
        new AlbumAssetsCallback()).executeAsync();

  }

  private final class AlbumAssetsCallback implements
      HttpCallback<ListResponseModel<AssetModel>> {

    @Override
    public void onSuccess(ListResponseModel<AssetModel> responseData) {
      if (responseData.getData().size() > 0) {
        gallery.setAssetCollection(responseData.getData());
        AssetModel assetModel = responseData.getData().get(0);
        GCHearts.get(getApplicationContext(), album, assetModel,
            new GetHeartsCallback(assetModel)).executeAsync();
      } else {
        Toast.makeText(
            getApplicationContext(),
            getApplicationContext().getResources().getString(
                R.string.no_photos_in_this_album),
            Toast.LENGTH_SHORT).show();
      }

    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      Toast.makeText(getApplicationContext(), R.string.http_error,
          Toast.LENGTH_SHORT).show();
    }

  }

  private final class GetHeartsCallback implements
      HttpCallback<ResponseModel<HeartModel>> {

    private AssetModel asset;

    private GetHeartsCallback(AssetModel asset) {
      this.asset = asset;
    }

    @Override
    public void onHttpError(ResponseStatus status) {
      ALog.d("Getting hearts error = " + status.getStatusMessage());

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onSuccess(ResponseModel<HeartModel> response) {
      if (AssetVotesPreferences.get().isAssetHeartById(asset.getId())) {
        isHearted = true;
        heart.setBackgroundDrawable(getResources().getDrawable(
            R.drawable.button_vote_on));
      } else {
        isHearted = false;
        heart.setBackgroundDrawable(getResources().getDrawable(
            R.drawable.button_vote));
      }
    }

  }

  private final class NewGalleryCallback implements GalleryCallback {

    @Override
    public void onPhotoChangeError(PhotoChangeErrorType error) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onPhotoChanged(int index, AssetModel asset) {
      GCHearts.get(getApplicationContext(), album, asset,
          new GetHeartsCallback(asset)).executeAsync();

    }

    @Override
    public void triggered(GestureEvent event) {
      // TODO Auto-generated method stub

    }

  }

  private final class HeartClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      synchronizeHearting();

    }
  }

  private void synchronizeHearting() {
    AssetModel asset = gallery.getSelectedItem();
    if (isHearted) {
      ALog.d("Unhearting photo");
      GCHearts.unheart(getApplicationContext(), album, asset, new UnheartCallback(asset))
          .executeAsync();
    } else {
      ALog.d("Hearting photo");
      GCHearts.heart(getApplicationContext(), album, asset, new HeartCallback(asset))
          .executeAsync();
    }

  }

  private final class UnheartCallback implements
      HttpCallback<ResponseModel<HeartModel>> {

    private AssetModel asset;

    private UnheartCallback(AssetModel asset) {
      this.asset = asset;
    }

    @Override
    public void onHttpError(ResponseStatus status) {
      ALog.d("Unhearting error = " + status.getStatusMessage());

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onSuccess(ResponseModel<HeartModel> response) {
      AssetVotesPreferences.get().unheartAssetById(asset.getId());
      heart.setBackgroundDrawable(getResources().getDrawable(
          R.drawable.button_vote));
      isHearted = false;

    }

  }

  private final class HeartCallback implements
      HttpCallback<ResponseModel<HeartModel>> {

    private AssetModel asset;

    private HeartCallback(AssetModel asset) {
      this.asset = asset;
    }

    @Override
    public void onHttpError(ResponseStatus status) {
      ALog.d("Hearting error = " + status.getStatusMessage());

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onSuccess(ResponseModel<HeartModel> response) {
      AssetVotesPreferences.get().heartAssetById(asset.getId());
      heart.setBackgroundDrawable(getResources().getDrawable(
          R.drawable.button_vote_on));
      isHearted = true;
    }

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    gallery.destroyGallery();
  }
}