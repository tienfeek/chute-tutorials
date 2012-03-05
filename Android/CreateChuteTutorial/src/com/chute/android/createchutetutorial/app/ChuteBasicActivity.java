package com.chute.android.createchutetutorial.app;

import com.chute.android.createchutetutorial.R;
import com.chute.android.createchutetutorial.intent.ChuteBasicActivityIntentWrapper;
import com.chute.android.createchutetutorial.model.RollsSingleton;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ChuteBasicActivity extends Activity {

	public static final String TAG = ChuteBasicActivity.class.getSimpleName();
	private final GCChuteModel chute = new GCChuteModel();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chute_basic);
		
		ChuteBasicActivityIntentWrapper wrapper = new ChuteBasicActivityIntentWrapper(getIntent());
		
		TextView chuteName = (TextView) findViewById(R.id.txtChute);
		chuteName.setText("Chute Name: " + wrapper.getChuteName());
		
		chute.setName(wrapper.getChuteName());
		chute.setPermissionView(2); //without password
		
		GCChutes.createChute(getApplicationContext(), chute,
				new BasicChuteCallback()).executeAsync();
	}
	
	private final class BasicChuteCallback implements GCHttpCallback<GCChuteModel> {

		@Override
		public void onSuccess(GCChuteModel responseData) {
			RollsSingleton.getInstance(getApplicationContext()).addChute(
					responseData);
				Toast.makeText(getApplicationContext(),
					"Chute Created!", Toast.LENGTH_SHORT).show();
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
