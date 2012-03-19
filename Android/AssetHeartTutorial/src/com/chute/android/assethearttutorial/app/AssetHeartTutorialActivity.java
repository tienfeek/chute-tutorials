package com.chute.android.assethearttutorial.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.chute.android.assethearttutorial.R;
import com.chute.android.assethearttutorial.view.HeartCheckbox;
import com.chute.android.gallery.components.GalleryViewFlipper;
import com.chute.android.gallery.components.GalleryViewFlipper.GalleryCallback;
import com.chute.android.gallery.components.GalleryViewFlipper.PhotoChangeErrorType;
import com.chute.android.gallery.zoom.PinchZoomListener.GestureEvent;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCAssetCollection;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCAssetModel;
import com.chute.sdk.model.GCHttpRequestParameters;

public class AssetHeartTutorialActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = AssetHeartTutorialActivity.class
			.getSimpleName();
	private HeartCheckbox heart;
	private GalleryViewFlipper gallery;
	private final String chuteId = "1946";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asset_heart_activity);

		// Test token, see GCAuthentication activity on how to authenticate
		GCAccountStore account = GCAccountStore
				.getInstance(getApplicationContext());
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

		gallery = (GalleryViewFlipper) findViewById(R.id.galleryId);
		gallery.setGalleryCallback(new NewGalleryCallback());
		heart = (HeartCheckbox) findViewById(R.id.btnHeart);

		GCChutes.Resources.assets(getApplicationContext(), chuteId,
				new AssetCollectionCallback()).executeAsync();

	}

	private final class AssetCollectionCallback implements
			GCHttpCallback<GCAssetCollection> {

		@Override
		public void onSuccess(GCAssetCollection responseData) {
			if (responseData.size() > 0) {
				gallery.setAssetCollection(responseData);
			} else {
				Toast.makeText(
						getApplicationContext(),
						getApplicationContext().getResources().getString(
								R.string.no_photos_in_this_chute),
						Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {

		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {

		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {

		}

	}

	private final class NewGalleryCallback implements GalleryCallback {

		@Override
		public void triggered(GestureEvent event) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPhotoChanged(int index, GCAssetModel asset) {
			heart.markHeartByAssetId(asset.getId());
		}

		@Override
		public void onPhotoChangeError(PhotoChangeErrorType error) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		heart.deleteObservers();
		gallery.destroyGallery();
	}
}