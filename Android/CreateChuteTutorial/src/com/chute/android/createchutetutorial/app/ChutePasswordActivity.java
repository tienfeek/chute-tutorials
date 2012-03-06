package com.chute.android.createchutetutorial.app;

import com.chute.android.createchutetutorial.R;
import com.chute.android.createchutetutorial.intent.ChutePasswordActivityIntentWrapper;
import com.chute.android.createchutetutorial.model.ChutesSingleton;
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

		chuteName.setText(getApplicationContext().getResources().getString(
				R.string.chute_name) + " "
				+ wrapper.getChuteName());
		chutePassword.setText(getApplicationContext().getResources().getString(
				R.string.chute_password) + " "
				+ wrapper.getChutePassword());

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
			ChutesSingleton.getInstance(getApplicationContext()).addChute(
					responseData);
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.chute_created), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.http_exception), Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.http_error), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.parsing_exception), Toast.LENGTH_SHORT)
					.show();
		}

	}
}
