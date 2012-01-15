package com.chute.android.tutorials.authentication;

import android.content.Intent;

public class AuthenticationActivity extends BaseLoginActivity {

    public static final String TAG = AuthenticationActivity.class.getSimpleName();

    @Override
    public void launchMainAppActivity() {
	// This method will be responsible for handling the authentication
	// success or if the user was previously authenticated sucessfully.
	final Intent intent = new Intent(getApplicationContext(), MainAppActivity.class);
	startActivity(intent);
	AuthenticationActivity.this.finish();
    }

}
