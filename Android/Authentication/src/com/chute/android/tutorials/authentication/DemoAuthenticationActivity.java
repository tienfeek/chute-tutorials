package com.chute.android.tutorials.authentication;

import android.content.Intent;

public class DemoAuthenticationActivity extends BaseLoginActivity {

	public static final String TAG = DemoAuthenticationActivity.class
			.getSimpleName();

	@Override
	public void launchMainAppActivity() {
		// This method will be responsible for handling the authentication
		// success or if the user was previously authenticated successfully.
		final Intent intent = new Intent(getApplicationContext(),
				MainAppActivity.class);
		startActivity(intent);
		DemoAuthenticationActivity.this.finish();
	}

}
