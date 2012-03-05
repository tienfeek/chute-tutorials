package com.chute.android.createchutetutorial.intent;

import android.content.Context;
import android.content.Intent;

import com.chute.android.createchutetutorial.app.ChutePermissionsActivity;

public class ChutePermissionsActivityIntentWrapper {

	public static final String TAG = ChutePermissionsActivityIntentWrapper.class
			.getSimpleName();
	
	private final Intent intent;

	public ChutePermissionsActivityIntentWrapper(Intent intent) {
		super();
		this.intent = intent;
	}

	public ChutePermissionsActivityIntentWrapper(Context packageContext, Class<?> cls) {
		super();
		intent = new Intent(packageContext, cls);
	}

	public ChutePermissionsActivityIntentWrapper(Context packageContext) {
		super();
		intent = new Intent(packageContext, ChutePermissionsActivity.class);
	}

	public Intent getIntent() {
		return intent;
	}
}
