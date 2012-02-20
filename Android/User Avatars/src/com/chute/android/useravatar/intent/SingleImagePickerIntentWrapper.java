package com.chute.android.useravatar.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chute.android.useravatar.app.SingleImagePickerActivity;

public class SingleImagePickerIntentWrapper {

    public static final int ACTIVITY_REQUEST_CODE = 1234;

    @SuppressWarnings("unused")
    private static final String TAG = SingleImagePickerIntentWrapper.class.getSimpleName();

    private final Intent intent;

    public SingleImagePickerIntentWrapper(Intent intent) {
	super();
	this.intent = intent;
    }

    public SingleImagePickerIntentWrapper(Context packageContext, Class<?> cls) {
	super();
	intent = new Intent(packageContext, cls);
    }

    public SingleImagePickerIntentWrapper(Context packageContext) {
	super();
	intent = new Intent(packageContext, SingleImagePickerActivity.class);
    }

    public Intent getIntent() {
	return intent;
    }

    public void startActivityForResult(Activity context) {
	context.startActivityForResult(intent, ACTIVITY_REQUEST_CODE);

    }

}
