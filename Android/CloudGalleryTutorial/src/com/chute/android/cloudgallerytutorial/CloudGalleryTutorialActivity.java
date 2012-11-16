package com.chute.android.cloudgallerytutorial;

import com.dg.libs.rest.authentication.TokenAuthenticationProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CloudGalleryTutorialActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = CloudGalleryTutorialActivity.class
			.getSimpleName();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TokenAuthenticationProvider.init(this);

		findViewById(R.id.startGallery).setOnClickListener(
				new OnStartClickListener());
	}

	private final class OnStartClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(),
					GalleryActivity.class);
			startActivity(intent);
		}

	}
}