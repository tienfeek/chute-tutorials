package com.chute.android.albumtutorial.ui.activity;

import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.intent.AlbumStatsActivityIntentWrapper;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.StatsModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AlbumStatsActivity extends Activity {

	public static final String TAG = AlbumStatsActivity.class.getSimpleName();
	private AlbumModel album = new AlbumModel();
	private AlbumStatsActivityIntentWrapper wrapper;
	private TextView viaUpload;
	private TextView viaImport;
	private TextView uploads;
	private TextView imports;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_stats_activity);

		viaUpload = (TextView) findViewById(R.id.txtViaUpload);
		viaImport = (TextView) findViewById(R.id.txtviaImport);
		uploads = (TextView) findViewById(R.id.txtUploads);
		imports = (TextView) findViewById(R.id.txtImports);

		wrapper = new AlbumStatsActivityIntentWrapper(getIntent());
		album.setId(wrapper.getAlbumId());

		GCAlbums.stats(getApplicationContext(), album, new AlbumStatsCallback())
				.executeAsync();
	}

	private final class AlbumStatsCallback implements
			HttpCallback<ResponseModel<StatsModel>> {

		@Override
		public void onSuccess(ResponseModel<StatsModel> responseData) {
			viaUpload.setText(getResources().getString(R.string.txt_via_upload)
					+ " "
					+ responseData.getData().getUserCounts().getViaUpload());
			viaImport.setText(getResources().getString(R.string.txt_via_import)
					+ " "
					+ responseData.getData().getUserCounts().getViaImport());
			uploads.setText(getResources().getString(R.string.txt_uploads)
					+ " "
					+ responseData.getData().getSourceCounts().getUploads());
			imports.setText(getResources().getString(R.string.txt_imports)
					+ " "
					+ responseData.getData().getSourceCounts().getImports());
		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			Log.e(TAG, getResources().getString(R.string.txt_error_occurred)
					+ " " + responseCode.getStatusMessage());
		}

	}
}
