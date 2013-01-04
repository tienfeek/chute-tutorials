package com.chute.android.albumtutorial.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chute.android.albumtutorial.Constants;
import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.intent.CreateAlbumActivityIntentWrapper;
import com.chute.android.albumtutorial.intent.AlbumActivityIntentWrapper;

public class AlbumTutorialActivity extends Activity {

	private Button createAlbum;
	private Button getAlbumList;
	private Button getAlbum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_tutorial_activity);

		createAlbum = (Button) findViewById(R.id.buttonCreateAlbum);
		getAlbumList = (Button) findViewById(R.id.buttonAlbumList);
		getAlbum = (Button) findViewById(R.id.buttonAlbum);

		createAlbum.setOnClickListener(new CreateAlbumClickListener());
		getAlbum.setOnClickListener(new GetAlbumClickListener());
		getAlbumList.setOnClickListener(new GetAlbumListClickListener());

	}

	private final class CreateAlbumClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			CreateAlbumActivityIntentWrapper wrapper = new CreateAlbumActivityIntentWrapper(
					AlbumTutorialActivity.this);
			wrapper.setAlbumName(Constants.TEST_ALBUM_NAME);
			wrapper.startActivity(AlbumTutorialActivity.this);
		}

	}

	private final class GetAlbumClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			AlbumActivityIntentWrapper wrapper = new AlbumActivityIntentWrapper(
					AlbumTutorialActivity.this);
			wrapper.setAlbumId(Constants.TEST_ALBUM_ID);
			wrapper.startActivity(AlbumTutorialActivity.this);
		}

	}

	private final class GetAlbumListClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(),
					AlbumListActivity.class);
			startActivity(intent);
		}

	}

}
