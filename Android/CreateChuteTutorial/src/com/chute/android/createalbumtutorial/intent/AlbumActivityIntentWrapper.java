package com.chute.android.createalbumtutorial.intent;

import com.chute.android.createalbumtutorial.app.AlbumActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class AlbumActivityIntentWrapper {

	public static final String TAG = AlbumActivityIntentWrapper.class
			.getSimpleName();

	private static final String KEY_ALBUM_NAME = "albumName";
	private static final String KEY_ALBUM_ID = "albumId";

	private final Intent intent;

	public AlbumActivityIntentWrapper(Intent intent) {
		super();
		this.intent = intent;
	}

	public AlbumActivityIntentWrapper(Context packageContext, Class<?> cls) {
		super();
		intent = new Intent(packageContext, cls);
	}

	public AlbumActivityIntentWrapper(Context packageContext) {
		super();
		intent = new Intent(packageContext, AlbumActivity.class);
	}

	public Intent getIntent() {
		return intent;
	}

	public String getAlbumName() {
		return intent.getExtras().getString(KEY_ALBUM_NAME);
	}

	public void setAlbumName(String name) {
		intent.putExtra(KEY_ALBUM_NAME, name);
	}

	public String getAlbumId() {
		return intent.getExtras().getString(KEY_ALBUM_ID);
	}

	public void setAlbumId(String id) {
		intent.putExtra(KEY_ALBUM_ID, id);
	}

	public void startActivity(Activity context) {
		context.startActivity(intent);

	}
}
