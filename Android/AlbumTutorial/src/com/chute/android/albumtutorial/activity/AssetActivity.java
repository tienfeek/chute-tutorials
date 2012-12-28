package com.chute.android.albumtutorial.activity;

import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.intent.AssetActivityIntentWrapper;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import darko.imagedownloader.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class AssetActivity extends Activity {

	public static final String TAG = AssetActivity.class.getSimpleName();
	private ImageView imageView;
	private ImageLoader loader;
	private AssetActivityIntentWrapper wrapper;
	private AlbumModel album = new AlbumModel();
	private AssetModel asset = new AssetModel();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asset_activity);

		imageView = (ImageView) findViewById(R.id.imageView);
		loader = ImageLoader.getLoader(getApplicationContext());
		wrapper = new AssetActivityIntentWrapper(getIntent());

		album.setId(wrapper.getAlbumId());
		asset.setId(wrapper.getAssetId());

		GCAlbums.Assets.get(getApplicationContext(), album, asset,
				new AssetCallback()).executeAsync();

	}

	private final class AssetCallback implements
			HttpCallback<ResponseModel<AssetModel>> {

		@Override
		public void onSuccess(ResponseModel<AssetModel> responseData) {
			loader.displayImage(responseData.getData().getThumbnail(),
					imageView);
		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			Log.e(TAG, "Error occured: " + responseCode.getStatusMessage());
		}

	}
}
