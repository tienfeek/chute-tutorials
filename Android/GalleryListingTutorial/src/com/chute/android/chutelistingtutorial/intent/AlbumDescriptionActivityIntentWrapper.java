package com.chute.android.chutelistingtutorial.intent;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.chute.android.chutelistingtutorial.app.AlbumDescriptionActivity;
import com.chute.sdk.v2.model.AlbumModel;

public class AlbumDescriptionActivityIntentWrapper extends IntentWrapper {

	public static final String TAG = AlbumDescriptionActivityIntentWrapper.class
			.getSimpleName();

	private static final String EXTRA_KEY_ALBUM_MODEL = "albumModel";

	public AlbumDescriptionActivityIntentWrapper(Intent intent) {
		super(intent);
	}

	public AlbumDescriptionActivityIntentWrapper(Context packageContext) {
		super(packageContext, AlbumDescriptionActivity.class);
	}

	public AlbumModel getAlbumModel() {
		return getIntent().getExtras().getParcelable(EXTRA_KEY_ALBUM_MODEL);
	}

	public void setAlbumModel(AlbumModel model) {
		getIntent().putExtra(EXTRA_KEY_ALBUM_MODEL, (Parcelable) model);
	}

	public void startActivity(Context context) {
		context.startActivity(getIntent());
	}
}
