package com.chute.android.useravatar.intent;

import com.chute.android.useravatar.app.DisplayAvatarActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class DisplayAvatarActivityIntentWrapper {

	@SuppressWarnings("unused")
	private static final String TAG = DisplayAvatarActivityIntentWrapper.class
			.getSimpleName();
	private static final String KEY_IMAGE_PATH = "keyImagePath";
	private static final String KEY_BITMAP = "keyBitmap";

	private final Intent intent;

	public DisplayAvatarActivityIntentWrapper(Intent intent) {
		super();
		this.intent = intent;
	}

	public DisplayAvatarActivityIntentWrapper(Context packageContext,
			Class<?> cls) {
		super();
		intent = new Intent(packageContext, cls);
	}

	public DisplayAvatarActivityIntentWrapper(Context packageContext) {
		super();
		intent = new Intent(packageContext, DisplayAvatarActivity.class);
	}

	public Intent getIntent() {
		return intent;
	}

	public void setImagePath(String path) {
		intent.putExtra(KEY_IMAGE_PATH, path);
	}

	public String getImagePath() {
		return intent.getExtras().getString(KEY_IMAGE_PATH);
	}

	public void setBitmap(Bitmap bitmap) {
		intent.putExtra(KEY_BITMAP, bitmap);
	}

	public Bitmap getBitmap() {
		return intent.getExtras().getParcelable(KEY_BITMAP);
	}

	public void startActivity(Activity context) {
		context.startActivity(intent);
	}

}
