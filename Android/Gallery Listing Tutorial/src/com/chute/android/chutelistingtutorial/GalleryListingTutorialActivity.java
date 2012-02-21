package com.chute.android.chutelistingtutorial;

import com.chute.android.gcchutelisting.util.intent.GalleryListingActivityIntentWrapper;
import com.chute.sdk.model.GCAccountStore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GalleryListingTutorialActivity extends Activity implements OnClickListener{
	 /** Called when the activity is first created. */

    @SuppressWarnings("unused")
    private static final String TAG = GalleryListingTutorialActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	GCAccountStore account = GCAccountStore.getInstance(getApplicationContext());
	account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

	Button showList = (Button) findViewById(R.id.btnShowList);
	showList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
	final GalleryListingActivityIntentWrapper wrapper = new GalleryListingActivityIntentWrapper(this);
	wrapper.startActivity(this);
    }
}