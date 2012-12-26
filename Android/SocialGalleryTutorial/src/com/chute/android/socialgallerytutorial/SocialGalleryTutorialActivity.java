package com.chute.android.socialgallerytutorial;

import com.chute.android.socialgallerytutorial.R;
import com.chute.android.socialgallery.util.Constants;
import com.chute.android.socialgallery.util.intent.SocialGalleryActivityIntentWrapper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SocialGalleryTutorialActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViewById(R.id.btnStart).setOnClickListener(
				new OnStartClickListener());
	}

	private final class OnStartClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			SocialGalleryActivityIntentWrapper wrapper = new SocialGalleryActivityIntentWrapper(
					SocialGalleryTutorialActivity.this);
			wrapper.setAlbumId(Constants.ALBUM_ID);
			wrapper.setAlbumName(Constants.ALBUM_NAME);
			wrapper.setAlbumShortcut(Constants.ALBUM_SHORTCUT);
			wrapper.startActivity(SocialGalleryTutorialActivity.this);
		}

	}

}
