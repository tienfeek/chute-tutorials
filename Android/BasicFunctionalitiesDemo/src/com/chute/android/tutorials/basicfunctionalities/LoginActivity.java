package com.chute.android.tutorials.basicfunctionalities;

import android.content.Intent;

import com.chute.android.tutorials.authentication.BaseLoginActivity;
import com.chute.sdk.api.authentication.GCAuthenticationFactory.AccountType;
import com.chute.sdk.model.GCAccount;

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

    @Override
    public void launchAuthenticationActivity(AccountType accountType) {
	// Add the credentials for your app from Chute
	GCAccount.getInstance(getApplicationContext()).startAuthenticationActivity(
		LoginActivity.this, accountType, "replace with profile permissions scope",
		"replace with your predefined callback url", "replace with client id",
		"replace with client secret");
    }
}
