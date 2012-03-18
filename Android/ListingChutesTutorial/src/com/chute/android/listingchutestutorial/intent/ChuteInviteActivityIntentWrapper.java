package com.chute.android.listingchutestutorial.intent;

import com.chute.android.listingchutestutorial.app.ChuteInviteActivity;

import android.content.Context;
import android.content.Intent;


public class ChuteInviteActivityIntentWrapper extends IntentWrapper {

	public static final String TAG = ChuteInviteActivityIntentWrapper.class
			.getSimpleName();
	
	 private static final String KEY_CHUTE_NAME = "key_chute_name";

	    private static final String EXTRA_KEY_CHUTE_ID = "chuteId";

	    public ChuteInviteActivityIntentWrapper(Intent intent) {
		super(intent);
	    }

	    public ChuteInviteActivityIntentWrapper(Context packageContext) {
		super(packageContext, ChuteInviteActivity.class);
	    }

	    public void setChuteId(String id) {
		getIntent().putExtra(EXTRA_KEY_CHUTE_ID, id);
	    }

	    public void setChuteName(String name) {
		getIntent().putExtra(KEY_CHUTE_NAME, name);
	    }

	    public String getChuteName() {
		return getIntent().getExtras().getString(KEY_CHUTE_NAME);
	    }

	    public String getChuteId() {
		return getIntent().getExtras().getString(EXTRA_KEY_CHUTE_ID);
	    }

	    public void startActivity(Context context) {
		context.startActivity(getIntent());
	    }
}
