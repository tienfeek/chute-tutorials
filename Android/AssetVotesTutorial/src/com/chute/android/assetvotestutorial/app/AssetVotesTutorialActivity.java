package com.chute.android.assetvotestutorial.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.chute.android.assetvotestutorial.R;
import com.chute.android.assetvotestutorial.view.VoteCheckbox;
import com.chute.android.gallery.components.GalleryViewFlipper;
import com.chute.android.gallery.components.GalleryViewFlipper.GalleryCallback;
import com.chute.android.gallery.components.GalleryViewFlipper.PhotoChangeErrorType;
import com.chute.android.gallery.zoom.PinchZoomListener.GestureEvent;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.requests.ListResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AssetVotesTutorialActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = AssetVotesTutorialActivity.class
			.getSimpleName();
	private VoteCheckbox vote;
	private GalleryViewFlipper gallery;
	private AlbumModel album = new AlbumModel();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asset_vote_activity);

		gallery = (GalleryViewFlipper) findViewById(R.id.galleryId);
		gallery.setGalleryCallback(new NewGalleryCallback());
		vote = (VoteCheckbox) findViewById(R.id.btnVote);

		album.setId("1946");
		GCAlbums.Assets.list(getApplicationContext(), album,
				new AlbumAssetsCallback()).executeAsync();

	}

	private final class AlbumAssetsCallback implements
			HttpCallback<ListResponseModel<AssetModel>> {

		@Override
		public void onSuccess(ListResponseModel<AssetModel> responseData) {
			if (responseData.getData().size() > 0) {
				gallery.setAssetCollection(responseData.getData());
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

	private final class NewGalleryCallback implements GalleryCallback {

		@Override
		public void onPhotoChangeError(PhotoChangeErrorType error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPhotoChanged(int index, AssetModel asset) {
			vote.markHeartByAssetId(asset.getId(), album.getId());

		}

		@Override
		public void triggered(GestureEvent event) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		vote.deleteObservers();
		gallery.destroyGallery();
	}
}