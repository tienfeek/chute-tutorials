package com.chute.android.listingchutestutorial.intent;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.chute.android.listingchutestutorial.app.ChuteDescriptionActivity;
import com.chute.sdk.model.GCChuteModel;

public class ChuteDescriptionActivityIntentWrapper extends IntentWrapper {

	public static final String TAG = ChuteDescriptionActivityIntentWrapper.class
			.getSimpleName();
	
	    private static final String EXTRA_KEY_CHUTE_MODEL = "chuteModel";

	    public ChuteDescriptionActivityIntentWrapper(Intent intent) {
		super(intent);
	    }

	    public ChuteDescriptionActivityIntentWrapper(Context packageContext) {
		super(packageContext, ChuteDescriptionActivity.class);
	    }
	    
	    public GCChuteModel getChuteModel() {
	    	return getIntent().getExtras().getParcelable(EXTRA_KEY_CHUTE_MODEL);
	    }
	    
	    public void setChuteModel(GCChuteModel model) {
	    	getIntent().putExtra(EXTRA_KEY_CHUTE_MODEL, (Parcelable) model);
	    }

	    public void startActivity(Context context) {
		context.startActivity(getIntent());
	    }
}
