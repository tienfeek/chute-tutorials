package com.chute.android.createchutetutorial.app;

import com.chute.android.createchutetutorial.Constants;
import com.chute.android.createchutetutorial.R;
import com.chute.android.createchutetutorial.R.id;
import com.chute.android.createchutetutorial.R.layout;
import com.chute.android.createchutetutorial.intent.ChuteBasicActivityIntentWrapper;
import com.chute.android.createchutetutorial.intent.ChutePasswordActivityIntentWrapper;
import com.chute.sdk.model.GCAccountStore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreateChuteTutorialActivity extends Activity {

	public static final String TAG = CreateChuteTutorialActivity.class
			.getSimpleName();
	private Button btnBasicChute;
	private Button btnPasswordChute;
	private Button btnPermissionsChute;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Test token, see GCAuthentication activity on how to authenticate
		GCAccountStore account = GCAccountStore
				.getInstance(getApplicationContext());
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

		btnBasicChute = (Button) findViewById(R.id.btnChuteBasic);
		btnPasswordChute = (Button) findViewById(R.id.btnChutePassword);
		btnPermissionsChute = (Button) findViewById(R.id.btnChutePermissions);

		btnBasicChute.setOnClickListener(new ChuteBasicClickListener());
		btnPasswordChute.setOnClickListener(new ChutePasswordClickListener());
		btnPermissionsChute
				.setOnClickListener(new ChutePermissionsClickListener());

	}

	private final class ChuteBasicClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final ChuteBasicActivityIntentWrapper wrapper = new ChuteBasicActivityIntentWrapper(
					CreateChuteTutorialActivity.this);
			wrapper.setChuteName(Constants.BASIC_CHUTE_NAME);
			wrapper.startActivity(CreateChuteTutorialActivity.this);
		}

	}

	private final class ChutePasswordClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final ChutePasswordActivityIntentWrapper wrapper = new ChutePasswordActivityIntentWrapper(
					CreateChuteTutorialActivity.this);
			wrapper.setChuteName(Constants.PASSWORD_CHUTE_NAME);
			wrapper.setChutePassword(Constants.PASSWORD_NAME);
			wrapper.startActivity(CreateChuteTutorialActivity.this);
		}

	}

	private final class ChutePermissionsClickListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}
}