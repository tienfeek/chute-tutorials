package com.chute.android.tutorials.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chute.sdk.api.authentication.GCAuthenticationActivity;
import com.chute.sdk.api.authentication.GCAuthenticationFactory.AccountType;
import com.chute.sdk.model.GCAccount;

public abstract class BaseLoginActivity extends Activity {
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

	if (GCAccount.getInstance(getApplicationContext()).isTokenValid()) {
	    facebookLogin.setVisibility(View.GONE);
	    twitterLogin.setVisibility(View.GONE);
	    launchMainAppActivity();
	} else {
	    facebookLogin.setVisibility(View.VISIBLE);
	    twitterLogin.setVisibility(View.VISIBLE);
	}
    }

    public class OnLoginClickListener implements OnClickListener {
	@Override
	public void onClick(final View v) {
	    final AccountType accountType = (AccountType) v.getTag();
	    launchAuthenticationActivity(accountType);
	}
    }

    public abstract void launchMainAppActivity();

    protected void launchAuthenticationActivity(AccountType accountType) {
	// Add the credentials for your app from Chute
	GCAccount.getInstance(getApplicationContext()).startAuthenticationActivity(
		BaseLoginActivity.this, accountType, "replace with profile permissions scope",
		"replace with your predefined callback url", "replace with client id",
		"replace with client secret");
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode == RESULT_OK) {
	    NotificationUtil.makeToast(getApplicationContext(), R.string.success);
	    launchMainAppActivity();
	    return;
	}
	if (resultCode == GCAuthenticationActivity.CODE_HTTP_EXCEPTION) {
	    NotificationUtil.makeConnectionProblemToast(getApplicationContext());
	} else if (resultCode == GCAuthenticationActivity.CODE_HTTP_ERROR) {
	    NotificationUtil.makeServerErrorToast(getApplicationContext());
	} else if (resultCode == GCAuthenticationActivity.CODE_PARSER_EXCEPTION) {
	    NotificationUtil.makeParserErrorToast(getApplicationContext());
	}
    }
}