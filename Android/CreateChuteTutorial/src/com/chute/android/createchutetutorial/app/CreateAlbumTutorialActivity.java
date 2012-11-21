package com.chute.android.createchutetutorial.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chute.android.createchutetutorial.Constants;
import com.chute.android.createchutetutorial.R;
import com.chute.android.createchutetutorial.intent.AlbumActivityIntentWrapper;

public class CreateAlbumTutorialActivity extends Activity {

	public static final String TAG = CreateAlbumTutorialActivity.class
			.getSimpleName();
	private Button btnCreateAlbum;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnCreateAlbum = (Button) findViewById(R.id.btnCreateAlbum);
		btnCreateAlbum.setOnClickListener(new CreateAlbumClickListener());

	}

	private final class CreateAlbumClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final AlbumActivityIntentWrapper wrapper = new AlbumActivityIntentWrapper(
					CreateAlbumTutorialActivity.this);
			wrapper.setAlbumName(Constants.BASIC_ALBUM_NAME);
			wrapper.startActivity(CreateAlbumTutorialActivity.this);
		}

	}

}