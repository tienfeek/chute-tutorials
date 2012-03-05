package com.chute.android.createchutetutorial.app;

import com.chute.android.createchutetutorial.R;
import com.chute.android.createchutetutorial.intent.ChutePasswordActivityIntentWrapper;
import com.chute.android.createchutetutorial.model.RollsSingleton;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ChutePasswordActivity extends Activity {

	public static final String TAG = ChutePasswordActivity.class
			.getSimpleName();
	private final GCChuteModel chute = new GCChuteModel();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chute_password);

		TextView chuteName = (TextView) findViewById(R.id.txtName);
		TextView chutePassword = (TextView) findViewById(R.id.txtPassword);

		ChutePasswordActivityIntentWrapper wrapper = new ChutePasswordActivityIntentWrapper(
				getIntent());

		chuteName.setText("Chute Name: " + wrapper.getChuteName());
		chutePassword.setText("Chute Password: " + wrapper.getChutePassword());

		chute.setName(wrapper.getChuteName());
		chute.setPassword(wrapper.getChutePassword());
		chute.setPermissionView(4);

		GCChutes.createChute(getApplicationContext(), chute,
				new PasswordChuteCallback()).executeAsync();

	}

	private final class PasswordChuteCallback implements
			GCHttpCallback<GCChuteModel> {

		@Override
		public void onSuccess(GCChuteModel responseData) {
			RollsSingleton.getInstance(getApplicationContext()).addChute(
					responseData);
			Toast.makeText(getApplicationContext(), "Chute Created!",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			// TODO Auto-generated method stub

		}

	}
}
