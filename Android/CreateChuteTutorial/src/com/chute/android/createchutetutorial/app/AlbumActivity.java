package com.chute.android.createchutetutorial.app;

import com.chute.android.createchutetutorial.R;
import com.chute.android.createchutetutorial.intent.AlbumActivityIntentWrapper;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AlbumActivity extends Activity {

	public static final String TAG = AlbumActivity.class.getSimpleName();
	private AlbumActivityIntentWrapper wrapper;
	private TextView albumName;
	private AlbumModel album = new AlbumModel();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_activity);

		wrapper = new AlbumActivityIntentWrapper(getIntent());

		albumName = (TextView) findViewById(R.id.txtName);
		album.setName(wrapper.getAlbumName());
		GCAlbums.create(getApplicationContext(), album,
				new CreateAlbumCallback()).executeAsync();

	}

	private final class CreateAlbumCallback implements
			HttpCallback<ResponseModel<AlbumModel>> {

		@Override
		public void onSuccess(ResponseModel<AlbumModel> responseData) {
			albumName.setText(responseData.getData().getName());
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.album_created), Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.http_error), Toast.LENGTH_SHORT).show();

		}

	}

}