package com.chute.android.tutorials.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chute.sdk.v2.api.authentication.AuthenticationFactory;
import com.chute.sdk.v2.api.authentication.TokenAuthenticationProvider;
import com.chute.sdk.v2.model.enums.AccountType;



public abstract class BaseLoginActivity extends Activity {

	// Fill with values from your chute developer account

	public static final String TAG = BaseLoginActivity.class.getSimpleName();

	private Button facebookLogin;
	private Button twitterLogin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		facebookLogin = (Button) findViewById(R.id.btnFacebook);
		facebookLogin.setTag(AccountType.FACEBOOK);
		twitterLogin = (Button) findViewById(R.id.btnTwitter);
		twitterLogin.setTag(AccountType.TWITTER);

		OnLoginClickListener loginClickListener = new OnLoginClickListener();
		facebookLogin.setOnClickListener(loginClickListener);
		twitterLogin.setOnClickListener(loginClickListener);

		if (TokenAuthenticationProvider.getInstance().isTokenValid()) {
			facebookLogin.setVisibility(View.GONE);
			twitterLogin.setVisibility(View.GONE);
			launchMainAppActivity();
		} else {
			facebookLogin.setVisibility(View.VISIBLE);
			twitterLogin.setVisibility(View.VISIBLE);
		}
	}

	private class OnLoginClickListener implements OnClickListener {
		@Override
		public void onClick(final View v) {
			final AccountType accountType = (AccountType) v.getTag();
			launchAuthenticationActivity(accountType);
		}
	}

	public abstract void launchMainAppActivity();

	protected void launchAuthenticationActivity(AccountType accountType) {
		AuthenticationFactory.getInstance()
				.startAuthenticationActivity(BaseLoginActivity.this,
						accountType);
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			NotificationUtil.makeToast(getApplicationContext(),
					R.string.success);
			launchMainAppActivity();
			return;
		}
	}
}