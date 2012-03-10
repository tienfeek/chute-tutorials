package com.chute.android.createchutetutorial.intent;

import com.chute.android.createchutetutorial.app.ChuteActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ChuteActivityIntentWrapper {

	public static final String TAG = ChuteActivityIntentWrapper.class
			.getSimpleName();

	private static final String KEY_CHUTE_NAME = "chuteName";
	private static final String KEY_CHUTE_PASSWORD = "chutePassword";

	private static final String KEY_CHUTE_FLAG = "chuteFlag";

	private final Intent intent;

	public ChuteActivityIntentWrapper(Intent intent) {
		super();
		this.intent = intent;
	}

	public ChuteActivityIntentWrapper(Context packageContext, Class<?> cls) {
		super();
		intent = new Intent(packageContext, cls);
	}

	public ChuteActivityIntentWrapper(Context packageContext) {
		super();
		intent = new Intent(packageContext, ChuteActivity.class);
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

	public int getChuteFlag() {
		return intent.getExtras().getInt(KEY_CHUTE_FLAG);
	}

	public void setChuteFlag(int flag) {
		intent.putExtra(KEY_CHUTE_FLAG, flag);
	}

	public void startActivity(Activity context) {
		context.startActivity(intent);

	}
}
