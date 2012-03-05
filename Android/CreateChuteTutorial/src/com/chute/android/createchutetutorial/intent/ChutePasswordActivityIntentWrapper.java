package com.chute.android.createchutetutorial.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chute.android.createchutetutorial.app.ChutePasswordActivity;

public class ChutePasswordActivityIntentWrapper {

	public static final String TAG = ChutePasswordActivityIntentWrapper.class
			.getSimpleName();

	public static final String KEY_CHUTE_NAME = "chuteName";
	public static final String KEY_CHUTE_PASSWORD = "chutePassword";

	private final Intent intent;

	public ChutePasswordActivityIntentWrapper(Intent intent) {
		super();
		this.intent = intent;
	}

	public ChutePasswordActivityIntentWrapper(Context packageContext,
			Class<?> cls) {
		super();
		intent = new Intent(packageContext, cls);
	}

	public ChutePasswordActivityIntentWrapper(Context packageContext) {
		super();
		intent = new Intent(packageContext, ChutePasswordActivity.class);
	}

	public Intent getIntent() {
		return intent;
	}

	public String getChuteName() {
		return intent.getExtras().getString(KEY_CHUTE_NAME);
	}

	public void setChuteName(String name) {
		intent.putExtra(KEY_CHUTE_NAME, name);
	}

	public String getChutePassword() {
		return intent.getExtras().getString(KEY_CHUTE_PASSWORD);
	}

	public void setChutePassword(String password) {
		intent.putExtra(KEY_CHUTE_PASSWORD, password);
	}

	public void startActivity(Activity context) {
		context.startActivity(intent);
	}
}
