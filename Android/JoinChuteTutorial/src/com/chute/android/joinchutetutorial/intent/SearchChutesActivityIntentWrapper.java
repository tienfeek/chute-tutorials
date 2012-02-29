package com.chute.android.joinchutetutorial.intent;

import com.chute.android.joinchutetutorial.app.SearchChutesActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class SearchChutesActivityIntentWrapper {

	public static final String TAG = SearchChutesActivityIntentWrapper.class
			.getSimpleName();
	public static final String KEY_SHORTCUT = "keyShortcut";
	public static final String KEY_DOMAIN = "keyDomain";

	private final Intent intent;

	public SearchChutesActivityIntentWrapper(Intent intent) {
		super();
		this.intent = intent;
	}

	public SearchChutesActivityIntentWrapper(Context packageContext,
			Class<?> cls) {
		super();
		intent = new Intent(packageContext, cls);
	}

	public SearchChutesActivityIntentWrapper(Context packageContext) {
		super();
		intent = new Intent(packageContext, SearchChutesActivity.class);
	}

	public Intent getIntent() {
		return intent;
	}

	public String getShortcut() {
		return intent.getExtras().getString(KEY_SHORTCUT);
	}

	public void setShortcut(String shortcut) {
		intent.putExtra(KEY_SHORTCUT, shortcut);
	}

	public String getDomain() {
		return intent.getExtras().getString(KEY_DOMAIN);
	}

	public void setDomain(String domain) {
		intent.putExtra(KEY_DOMAIN, domain);
	}

	public void startActivity(Activity context) {
		context.startActivity(intent);
	}
}
