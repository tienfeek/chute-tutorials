package com.chute.android.albumtutorial.ui.activity;

import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.intent.CreateAlbumActivityIntentWrapper;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAlbumActivity extends Activity {

	public static final String TAG = CreateAlbumActivity.class.getSimpleName();
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

	private CreateAlbumActivityIntentWrapper wrapper;
	private Button buttonUpdate;
	private Button buttonOk;

	private String albumId;

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

		// buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
		// buttonOk = (Button) findViewById(R.id.buttonOk);

		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
		LinearLayout linearLayout = new LinearLayout(this);
		LayoutParams linearParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		linearParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);
		relativeLayout.addView(linearLayout, linearParams);

		buttonUpdate = new Button(this);
		buttonUpdate.setText(getResources().getString(R.string.btn_update));
		buttonOk = new Button(this);
		buttonOk.setText(getResources().getString(R.string.btn_ok));
		android.widget.LinearLayout.LayoutParams buttonParams = new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		buttonParams.weight = 1;
		linearLayout.addView(buttonUpdate, buttonParams);
		linearLayout.addView(buttonOk, buttonParams);

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
			id.setText(getResources().getString(R.string.txt_id) + " "
					+ responseData.getData().getId());
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
			id.setText(getResources().getString(R.string.txt_id) + " "
					+ responseData.getData().getId());
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

}
