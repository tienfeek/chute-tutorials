package com.chute.android.tutorials.basicfunctionalities;

import android.content.Intent;

import com.chute.android.tutorials.authentication.BaseLoginActivity;

public class LoginActivity extends BaseLoginActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    public void launchMainAppActivity() {
	// This method will be responsible for handling the authentication
	// success or if the user was previously authenticated sucessfully.
	final Intent intent = new Intent(getApplicationContext(),
		BasicFunctionalitiesDemoActivity.class);
	startActivity(intent);
	LoginActivity.this.finish();
    }
}
