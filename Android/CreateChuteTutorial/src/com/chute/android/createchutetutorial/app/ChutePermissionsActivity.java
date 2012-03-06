package com.chute.android.createchutetutorial.app;

import com.chute.android.createchutetutorial.R;
import com.chute.android.createchutetutorial.intent.ChutePermissionsActivityIntentWrapper;
import com.chute.android.createchutetutorial.model.ChutesSingleton;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

public class ChutePermissionsActivity extends Activity {

	public static final String TAG = ChutePermissionsActivity.class
			.getSimpleName();
	private final GCChuteModel chute = new GCChuteModel();
	private ChutePermissionsActivityIntentWrapper wrapper;
	private TextView chuteName;
	private TextView chutePassword;
	private TextView permissionPhotos;
	private TextView permissionMembers;
	private TextView permissionComments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chute_permissions);

		wrapper = new ChutePermissionsActivityIntentWrapper(getIntent());

		chuteName = (TextView) findViewById(R.id.txtName);
		chuteName.setText(getApplicationContext().getResources().getString(
				R.string.chute_name) + " "
				+ wrapper.getChuteName());
		chute.setName(wrapper.getChuteName());

		chutePassword = (TextView) findViewById(R.id.txtPassword);
		if (TextUtils.isEmpty(wrapper.getChutePassword())) {
			chute.setPermissionView(2);
		} else {
			chutePassword.setText(getApplicationContext().getResources()
					.getString(R.string.chute_password) + " "
					+ wrapper.getChutePassword());
			chute.setPassword(wrapper.getChutePassword());
			chute.setPermissionView(4);
		}

		permissionPhotos = (TextView) findViewById(R.id.txtPermissionPhotos);
		chute.setPermissionAddPhotos(2);
		permissionPhotos.setText(getApplicationContext().getResources()
				.getString(R.string.permission_to_add_photos));

		permissionMembers = (TextView) findViewById(R.id.txtPermissionMembers);
		chute.setPermissionAddMembers(2);
		permissionMembers.setText(getApplicationContext().getResources()
				.getString(R.string.permission_to_add_members));

		permissionComments = (TextView) findViewById(R.id.txtPermissionComments);
		chute.setPermissionAddComments(2);
		permissionComments.setText(getApplicationContext().getResources()
				.getString(R.string.permission_to_wite_comments));

		GCChutes.createChute(getApplicationContext(), chute,
				new ChutePermissionsCallback()).executeAsync();

	}

	private final class ChutePermissionsCallback implements
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
