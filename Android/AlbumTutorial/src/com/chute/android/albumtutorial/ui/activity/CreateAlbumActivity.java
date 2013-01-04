package com.chute.android.albumtutorial.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.intent.CreateAlbumActivityIntentWrapper;
import com.chute.android.albumtutorial.ui.fragment.AlbumFragment;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class CreateAlbumActivity extends FragmentActivity {

	public static final String TAG = CreateAlbumActivity.class.getSimpleName();
	private AlbumModel album = new AlbumModel();
	private CreateAlbumActivityIntentWrapper wrapper;
	private Button buttonUpdate;
	private Button buttonOk;
	private String albumId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_album_activity);

		AlbumFragment fragment = (AlbumFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.add(R.id.linearFragment, new AlbumFragment());
		ft.commit();

		buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
		buttonOk = (Button) findViewById(R.id.buttonOk);

		// RelativeLayout relativeLayout = (RelativeLayout)
		// findViewById(R.id.realtiveLayout);
		// LinearLayout linearLayout = new LinearLayout(this);
		// LayoutParams linearParams = new
		// LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT);
		// linearParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
		// RelativeLayout.TRUE);
		// relativeLayout.addView(linearLayout, linearParams);
		//
		// buttonUpdate = new Button(this);
		// buttonUpdate.setText(getResources().getString(R.string.btn_update));
		// buttonOk = new Button(this);
		// buttonOk.setText(getResources().getString(R.string.btn_ok));
		// android.widget.LinearLayout.LayoutParams buttonParams = new
		// android.widget.LinearLayout.LayoutParams(
		// android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
		// android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		// buttonParams.weight = 1;
		// linearLayout.addView(buttonUpdate, buttonParams);
		// linearLayout.addView(buttonOk, buttonParams);

		buttonUpdate.setOnClickListener(new ButtonUpdateClickListener());
		buttonOk.setOnClickListener(new ButtonOkClickListener());

		wrapper = new CreateAlbumActivityIntentWrapper(getIntent());
		album.setName(wrapper.getAlbumName());
		/**
		 * If not specifically set, the default value of moderate_comments and
		 * moderate_media flags is false
		 */
		album.setModerateComments(true);
		album.setModerateMedia(true);
		GCAlbums.create(getApplicationContext(), album,
				new CreateAlbumCallback()).executeAsync();
	}

	private final class CreateAlbumCallback implements
			HttpCallback<ResponseModel<AlbumModel>> {

		@Override
		public void onSuccess(ResponseModel<AlbumModel> responseData) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.txt_album_created),
					Toast.LENGTH_SHORT).show();
			setAlbumModel(responseData);

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

	private final class ButtonUpdateClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			album.setName("New updated Name");
			Log.d("debug", albumId);
			album.setId(albumId);
			GCAlbums.update(getApplicationContext(), album,
					new UpdateAlbumCallback()).executeAsync();
		}

	}

	private final class UpdateAlbumCallback implements
			HttpCallback<ResponseModel<AlbumModel>> {

		@Override
		public void onSuccess(ResponseModel<AlbumModel> responseData) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.txt_album_updated),
					Toast.LENGTH_SHORT).show();
			setAlbumModel(responseData);

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

	private final class ButtonOkClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			finish();
		}

	}

	private void setAlbumModel(ResponseModel<AlbumModel> responseData) {
		albumId = responseData.getData().getId();
		album.setId(responseData.getData().getId());
		album.setLinks(responseData.getData().getLinks());
		album.setCreatedAt(responseData.getData().getCreatedAt());
		album.setUpdatedAt(responseData.getData().getUpdatedAt());
		album.setShortcut(responseData.getData().getShortcut());
		album.setName(responseData.getData().getName());
		album.setDescription(responseData.getData().getDescription());
		album.setUser(responseData.getData().getUser());
		album.setModerateComments(responseData.getData().isModerateComments());
		album.setModerateMedia(responseData.getData().isModerateMedia());
		if (responseData.getData().getCounters() != null) {
			album.setCounters(responseData.getData().getCounters());
		}
	}

	public AlbumModel getAlbumModel() {
		return album;
	}
}
