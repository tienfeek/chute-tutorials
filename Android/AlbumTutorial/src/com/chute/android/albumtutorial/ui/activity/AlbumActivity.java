package com.chute.android.albumtutorial.ui.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.chute.android.albumtutorial.Constants;
import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.intent.AlbumActivityIntentWrapper;
import com.chute.android.albumtutorial.intent.AlbumStatsActivityIntentWrapper;
import com.chute.android.albumtutorial.intent.AssetActivityIntentWrapper;
import com.chute.android.albumtutorial.intent.AssetListActivityIntentWrapper;
import com.chute.android.albumtutorial.ui.fragment.AlbumFragment;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AlbumActivity extends FragmentActivity {

	public static final String TAG = AlbumActivity.class.getSimpleName();
	private AlbumModel album = new AlbumModel();
	private AlbumActivityIntentWrapper wrapper;
	private AlbumFragment albumFragment;

	String albumId;
	String assetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_activity);

		albumFragment = (AlbumFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment);

		wrapper = new AlbumActivityIntentWrapper(getIntent());
		album.setId(wrapper.getAlbumId());
		GCAlbums.get(getApplicationContext(), album, new GetAlbumCallback())
				.executeAsync();
	}

	private final class GetAlbumCallback implements
			HttpCallback<ResponseModel<AlbumModel>> {

		@Override
		public void onSuccess(ResponseModel<AlbumModel> responseData) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.txt_album_created),
					Toast.LENGTH_SHORT).show();
			albumFragment.setAlbumModel(responseData.getData());
			albumId = responseData.getData().getId();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		assetId = "98258725";
		ArrayList<String> idList = new ArrayList<String>();
		idList.add(assetId);
		switch (item.getItemId()) {
		case R.id.getAsset:
			AssetActivityIntentWrapper assetWrapper = new AssetActivityIntentWrapper(
					AlbumActivity.this);
			assetWrapper.setAlbumId(albumId);
			assetWrapper.setAssetId(Constants.TEST_ASSET_ID); // TODO set
																// assetId
			assetWrapper.startActivity(AlbumActivity.this);
			break;
		case R.id.getAssetList:
			AssetListActivityIntentWrapper assetListWrapper = new AssetListActivityIntentWrapper(
					AlbumActivity.this);
			assetListWrapper.setAlbumId(albumId);
			assetListWrapper.startActivity(AlbumActivity.this);
			break;
		case R.id.addAssets:
			GCAlbums.Assets.add(getApplicationContext(), album, idList,
					new AddAssetsCallback()).executeAsync();
			break;
		case R.id.removeAssets:
			GCAlbums.Assets.remove(getApplicationContext(), album, idList,
					new RemoveAssetsCallback()).executeAsync();
			break;
		case R.id.getStats:
			AlbumStatsActivityIntentWrapper albumStatsWrapper = new AlbumStatsActivityIntentWrapper(
					AlbumActivity.this);
			albumStatsWrapper.setAlbumId(albumId);
			albumStatsWrapper.startActivity(AlbumActivity.this);
			break;
		}
		return true;
	}

	private final class AddAssetsCallback implements HttpCallback<Void> {

		@Override
		public void onHttpError(ResponseStatus responseStatus) {
			Log.e(TAG, "Error occurred: " + responseStatus.getStatusMessage());

		}

		@Override
		public void onSuccess(Void responseData) {
			Toast.makeText(getApplicationContext(),
					R.string.add_assets_success, Toast.LENGTH_LONG).show();
		}

	}

	private final class RemoveAssetsCallback implements HttpCallback<Void> {

		@Override
		public void onHttpError(ResponseStatus responseStatus) {
			Log.e(TAG, "Error occurred: " + responseStatus.getStatusMessage());

		}

		@Override
		public void onSuccess(Void responseData) {
			Toast.makeText(getApplicationContext(),
					R.string.remove_assets_success, Toast.LENGTH_LONG).show();
		}

	}

}
