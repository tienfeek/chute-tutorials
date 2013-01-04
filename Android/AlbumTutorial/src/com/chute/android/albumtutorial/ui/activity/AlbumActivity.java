package com.chute.android.albumtutorial.ui.activity;

import com.chute.android.albumtutorial.Constants;
import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.intent.AlbumActivityIntentWrapper;
import com.chute.android.albumtutorial.intent.AlbumStatsActivityIntentWrapper;
import com.chute.android.albumtutorial.intent.AssetActivityIntentWrapper;
import com.chute.android.albumtutorial.intent.AssetListActivityIntentWrapper;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AlbumActivity extends Activity {

	public static final String TAG = AlbumActivity.class.getSimpleName();
	private AlbumModel album = new AlbumModel();
	private TextView id;
	private TextView links;
	private TextView createdAt;
	private TextView updatedAt;
	private TextView shortcut;
	private TextView name;
	private TextView description;
	private TextView user;
	private TextView moderateComments;
	private TextView moderateMedia;
	private TextView counters;
	private AlbumActivityIntentWrapper wrapper;

	String albumId;
	String assetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_album_activity);

		id = (TextView) findViewById(R.id.txtId);
		links = (TextView) findViewById(R.id.txtLinks);
		createdAt = (TextView) findViewById(R.id.txtCreatedAt);
		updatedAt = (TextView) findViewById(R.id.txtUpdatedAt);
		shortcut = (TextView) findViewById(R.id.txtShortcut);
		name = (TextView) findViewById(R.id.txtName);
		description = (TextView) findViewById(R.id.txtDescription);
		user = (TextView) findViewById(R.id.txtUser);
		moderateComments = (TextView) findViewById(R.id.txtModerateComments);
		moderateMedia = (TextView) findViewById(R.id.txtModerateMedia);
		counters = (TextView) findViewById(R.id.txtCounters);

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
			id.setText("Id: " + responseData.getData().getId());
			links.setText(getResources().getString(R.string.txt_links) + " "
					+ responseData.getData().getLinks().toString());
			createdAt.setText(getResources().getString(R.string.txt_created_at)
					+ " " + responseData.getData().getCreatedAt());
			updatedAt.setText(getResources().getString(R.string.txt_updated_at)
					+ " " + responseData.getData().getUpdatedAt());
			shortcut.setText(getResources().getString(R.string.txt_shortcut)
					+ " " + responseData.getData().getShortcut());
			name.setText(getResources().getString(R.string.txt_name) + " "
					+ responseData.getData().getName());
			description.setText(getResources().getString(
					R.string.txt_description)
					+ " " + responseData.getData().getDescription());
			user.setText(getResources().getString(R.string.txt_user) + " "
					+ responseData.getData().getUser().toString());
			moderateComments
					.setText(getResources().getString(
							R.string.txt_moderate_comments)
							+ " "
							+ (responseData.getData().isModerateComments() == true ? "true"
									: "false"));
			moderateMedia
					.setText(getResources().getString(
							R.string.txt_moderate_media)
							+ " "
							+ (responseData.getData().isModerateMedia() == true ? "true"
									: "false"));
			counters.setText(getResources().getString(R.string.txt_counters)
					+ " " + responseData.getData().getCounters().toString());

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
		switch (item.getItemId()) {
		case R.id.getAsset:
			AssetActivityIntentWrapper assetWrapper = new AssetActivityIntentWrapper(
					AlbumActivity.this);
			assetWrapper.setAlbumId(albumId);
			assetWrapper.setAssetId(Constants.TEST_ASSET_ID); // TODO set assetId
			assetWrapper.startActivity(AlbumActivity.this);
			break;
		case R.id.getAssetList:
			AssetListActivityIntentWrapper assetListWrapper = new AssetListActivityIntentWrapper(
					AlbumActivity.this);
			assetListWrapper.setAlbumId(albumId);
			assetListWrapper.startActivity(AlbumActivity.this);
			break;
		case R.id.addAssets:
			break;
		case R.id.removeAssets:
			// TODO
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

}
