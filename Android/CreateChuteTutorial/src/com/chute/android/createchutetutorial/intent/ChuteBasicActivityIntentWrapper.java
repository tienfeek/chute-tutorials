package com.chute.android.createchutetutorial.intent;

import com.chute.android.createchutetutorial.app.ChuteBasicActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ChuteBasicActivityIntentWrapper {

	public static final String TAG = ChuteBasicActivityIntentWrapper.class
			.getSimpleName();

	private static final String KEY_CHUTE_NAME = "chuteName";

	private final Intent intent;

	public ChuteBasicActivityIntentWrapper(Intent intent) {
		super();
		this.intent = intent;
	}

	public ChuteBasicActivityIntentWrapper(Context packageContext, Class<?> cls) {
		super();
		intent = new Intent(packageContext, cls);
	}

	public ChuteBasicActivityIntentWrapper(Context packageContext) {
		super();
		intent = new Intent(packageContext, ChuteBasicActivity.class);
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

	public void startActivity(Activity context) {
		context.startActivity(intent);

	}
}
