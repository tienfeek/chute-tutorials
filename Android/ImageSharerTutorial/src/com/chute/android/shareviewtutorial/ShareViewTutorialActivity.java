package com.chute.android.shareviewtutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.chute.android.imagesharer.intent.ShareActivityIntentWrapper;
import com.chute.android.imagesharer.util.Constants;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.requests.ListResponseModel;
import com.dg.libs.rest.authentication.TokenAuthenticationProvider;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class ShareViewTutorialActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = ShareViewTutorialActivity.class
			.getSimpleName();
	private AlbumModel album = new AlbumModel();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TokenAuthenticationProvider.init(this);

		Button share = (Button) findViewById(R.id.btnShare);
		share.setOnClickListener(new OnShareClickListener());
	}

	private final class OnShareClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			album.setId(Constants.ALBUM_ID);
			GCAlbums.Assets.list(getApplicationContext(), album,
					new AlbumAssetsCallback()).executeAsync();
		}

	}

	private final class AlbumAssetsCallback implements
			HttpCallback<ListResponseModel<AssetModel>> {

		@Override
		public void onSuccess(ListResponseModel<AssetModel> responseData) {
			if (responseData.getData().size() > 0) {
				ShareActivityIntentWrapper wrapper = new ShareActivityIntentWrapper(
						ShareViewTutorialActivity.this);
				wrapper.setAssetShareUrl(responseData.getData().get(0).getUrl());
				wrapper.setAlbumId(Constants.ALBUM_ID);
				wrapper.setAlbumName(Constants.ALBUM_NAME);
				wrapper.setAlbumShortcut(Constants.ALBUM_SHORTCUT);
				wrapper.startActivity(ShareViewTutorialActivity.this);
			} else {
				Toast.makeText(
						getApplicationContext(),
						getApplicationContext().getResources().getString(
								R.string.no_photos_in_this_chute),
						Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			Toast.makeText(getApplicationContext(), R.string.http_error,
					Toast.LENGTH_SHORT).show();

		}

	}
}