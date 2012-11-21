package com.chute.android.chutelistingtutorial.intent;

import com.chute.android.chutelistingtutorial.app.AlbumInviteActivity;

import android.content.Context;
import android.content.Intent;

public class AlbumInviteActivityIntentWrapper extends IntentWrapper {

	public static final String TAG = AlbumInviteActivityIntentWrapper.class
			.getSimpleName();

	private static final String KEY_ALBUM_NAME = "key_album_name";

	private static final String EXTRA_KEY_ALBUM_ID = "albumId";

	public AlbumInviteActivityIntentWrapper(Intent intent) {
		super(intent);
	}

	public AlbumInviteActivityIntentWrapper(Context packageContext) {
		super(packageContext, AlbumInviteActivity.class);
	}

	public void setAlbumId(String id) {
		getIntent().putExtra(EXTRA_KEY_ALBUM_ID, id);
	}

	public void setAlbumName(String name) {
		getIntent().putExtra(KEY_ALBUM_NAME, name);
	}

	public String getAlbumName() {
		return getIntent().getExtras().getString(KEY_ALBUM_NAME);
	}

	public String getAlbumId() {
		return getIntent().getExtras().getString(EXTRA_KEY_ALBUM_ID);
	}

	public void startActivity(Context context) {
		context.startActivity(getIntent());
	}
}
