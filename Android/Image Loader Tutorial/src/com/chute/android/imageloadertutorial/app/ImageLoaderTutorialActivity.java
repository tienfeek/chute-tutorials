package com.chute.android.imageloadertutorial.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chute.android.imageloadertutorial.R;
import com.chute.android.imageloadertutorial.intent.LoadImageActivityIntentWrapper;
import com.chute.sdk.model.GCAccountStore;

public class ImageLoaderTutorialActivity extends Activity {

	private Button btnSdcard;
	private Button btnUrl;
	private Button btnChutePhotoUrl;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Test token, see GCAuthentication activity on how to authenticate
		GCAccountStore account = GCAccountStore
				.getInstance(getApplicationContext());
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

		btnSdcard = (Button) findViewById(R.id.btnSdcard);
		btnSdcard.setOnClickListener(new OnButtonSdcardClicked());

		btnUrl = (Button) findViewById(R.id.btnUrl);
		btnUrl.setOnClickListener(new OnButtonUrlClicked());

		btnChutePhotoUrl = (Button) findViewById(R.id.btnChutePhotoUrl);
		btnChutePhotoUrl.setOnClickListener(new OnButtonChutePhotoUrlClicked());
	}

	private final class OnButtonSdcardClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
					ImageLoaderTutorialActivity.this);
			wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_SDCARD);
			wrapper.startActivity(ImageLoaderTutorialActivity.this);
		}

	}

	private final class OnButtonUrlClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
					ImageLoaderTutorialActivity.this);
			wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_URL);
			wrapper.startActivity(ImageLoaderTutorialActivity.this);
		}

	}

	private final class OnButtonChutePhotoUrlClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
					ImageLoaderTutorialActivity.this);
			wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_CHUTE_URL);
			wrapper.startActivity(ImageLoaderTutorialActivity.this);
		}

	}
}