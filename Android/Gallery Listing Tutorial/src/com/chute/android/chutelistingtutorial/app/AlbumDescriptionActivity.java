package com.chute.android.chutelistingtutorial.app;

import com.chute.android.chutelistingtutorial.R;
import com.chute.android.chutelistingtutorial.intent.AlbumDescriptionActivityIntentWrapper;
import com.chute.sdk.v2.model.AlbumModel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AlbumDescriptionActivity extends Activity {

	public static final String TAG = AlbumDescriptionActivity.class
			.getSimpleName();
	private AlbumDescriptionActivityIntentWrapper wrapper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_description_activity);

		wrapper = new AlbumDescriptionActivityIntentWrapper(getIntent());

		init();
	}

	public void init() {
		AlbumModel model = wrapper.getAlbumModel();

		TextView albumId = (TextView) findViewById(R.id.albumId);
		albumId.setText(getApplicationContext().getResources().getString(
				R.string.id)
				+ " " + model.getId());

		TextView name = (TextView) findViewById(R.id.name);
		name.setText(getApplicationContext().getResources().getString(
				R.string.name)
				+ " " + model.getName());

		TextView links = (TextView) findViewById(R.id.linksId);
		links.setText(getApplicationContext().getResources().getString(
				R.string.links)
				+ " " + model.getLinks().toString());

		TextView counters = (TextView) findViewById(R.id.countersId);
		counters.setText(getApplicationContext().getResources().getString(
				R.string.counters)
				+ " " + model.getCounters().toString());

		TextView moderateMedia = (TextView) findViewById(R.id.moderateMedia);
		moderateMedia.setText(getApplicationContext().getResources().getString(
				R.string.moderate_media)
				+ " " + model.isModerateMedia());

		TextView user = (TextView) findViewById(R.id.user);
		user.setText(getApplicationContext().getResources().getString(
				R.string.user)
				+ " " + model.getUser().toString());

		TextView moderateComments = (TextView) findViewById(R.id.moderateComments);
		moderateComments.setText(getApplicationContext().getResources()
				.getString(R.string.moderate_comments)
				+ " "
				+ model.isModerateComments());

		TextView createdAt = (TextView) findViewById(R.id.createdAt);
		createdAt.setText(getApplicationContext().getResources().getString(
				R.string.created_at)
				+ " " + model.getCreatedAt());

		TextView shortcut = (TextView) findViewById(R.id.shortcut);
		shortcut.setText(getApplicationContext().getResources().getString(
				R.string.shortcut)
				+ " " + model.getShortcut());

	}

}
