package com.chute.android.imagegridtutorial.app;

import com.chute.android.imagegrid.intent.ImageGridIntentWrapper;
import com.chute.android.imagegridtutorial.R;
import com.dg.libs.rest.authentication.TokenAuthenticationProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImageGridTutorialActivity extends Activity implements
		OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TokenAuthenticationProvider.init(this);

		Button showAssetsButton = (Button) findViewById(R.id.btnShowAssets);
		showAssetsButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		ImageGridIntentWrapper wrapper = new ImageGridIntentWrapper(this);
		wrapper.setAlbumId("1946"); // Replace with album id
		wrapper.startActivity(this);
	}

}