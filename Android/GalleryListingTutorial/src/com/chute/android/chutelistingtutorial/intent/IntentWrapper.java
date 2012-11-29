package com.chute.android.chutelistingtutorial.intent;

import android.content.Context;
import android.content.Intent;

public class IntentWrapper {
    @SuppressWarnings("unused")
    private static final String TAG = IntentWrapper.class.getSimpleName();

    private final Intent intent;

    public IntentWrapper(Intent intent) {
	super();
	this.intent = intent;
    }

    public IntentWrapper(Context packageContext, Class<?> cls) {
	super();
	intent = new Intent(packageContext, cls);
    }

    public Intent getIntent() {
	return intent;
    }
}
