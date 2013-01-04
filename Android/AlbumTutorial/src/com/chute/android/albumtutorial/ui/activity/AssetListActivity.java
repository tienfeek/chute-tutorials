package com.chute.android.albumtutorial.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.intent.AssetListActivityIntentWrapper;
import com.chute.android.albumtutorial.ui.adapter.AssetListAdapter;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.requests.ListResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AssetListActivity extends Activity {

	public static final String TAG = AssetListActivity.class.getSimpleName();
	private AlbumModel album = new AlbumModel();
	private AssetListActivityIntentWrapper wrapper;
	private GridView grid;
	private AssetListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asset_list_activity);

		grid = (GridView) findViewById(R.id.gridView);
		wrapper = new AssetListActivityIntentWrapper(getIntent());
		album.setId(wrapper.getAlbumId());
		GCAlbums.Assets.list(getApplicationContext(), album,
				new AssetsCallback()).executeAsync();
	}

	private final class AssetsCallback implements
			HttpCallback<ListResponseModel<AssetModel>> {

		@Override
		public void onSuccess(ListResponseModel<AssetModel> responseData) {
			adapter = new AssetListAdapter(AssetListActivity.this,
					responseData.getData());
			grid.setAdapter(adapter);
			grid.setOnItemClickListener(new GridClickedListener());
		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			Toast.makeText(getApplicationContext(),
					responseCode.getStatusMessage(), Toast.LENGTH_SHORT).show();
		}

	}

	private final class GridClickedListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			finish();
		}

	}
}
