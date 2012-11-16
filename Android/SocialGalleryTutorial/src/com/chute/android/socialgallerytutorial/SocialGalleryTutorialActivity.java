package com.chute.android.socialgallerytutorial;

import com.chute.android.socialgallery.R;
import com.chute.android.socialgallery.util.Constants;
import com.chute.android.socialgallery.util.intent.SocialGalleryActivityIntentWrapper;
import com.chute.sdk.v2.model.AccountStore;

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

		// Test token, see GCAuthenticationActivity on how to authenticate
		AccountStore account = AccountStore
				.getInstance(getApplicationContext());
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

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
