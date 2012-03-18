package com.chute.android.createchutetutorial.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.chute.android.createchutetutorial.R;
import com.chute.android.createchutetutorial.intent.ChuteBasicActivityIntentWrapper;
import com.chute.android.createchutetutorial.model.ChutesSingleton;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;

public class ChuteBasicActivity extends Activity {

    public static final String TAG = ChuteBasicActivity.class.getSimpleName();
    private final GCChuteModel chute = new GCChuteModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.chute_basic);

	ChuteBasicActivityIntentWrapper wrapper = new ChuteBasicActivityIntentWrapper(getIntent());

	TextView chuteName = (TextView) findViewById(R.id.txtChute);
	chuteName.setText(getApplicationContext().getResources().getString(R.string.chute_name)
		+ " " + wrapper.getChuteName());

	chute.setName(wrapper.getChuteName());
	chute.setPermissionView(2); // without password

	GCChutes.createChute(getApplicationContext(), chute, new BasicChuteCallback())
		.executeAsync();
    }

    private final class BasicChuteCallback implements GCHttpCallback<GCChuteModel> {

	@Override
	public void onSuccess(GCChuteModel responseData) {
	    ChutesSingleton.getInstance(getApplicationContext()).addChute(responseData);
	    Toast.makeText(getApplicationContext(),
		    getApplicationContext().getResources().getString(R.string.chute_created),
		    Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
	    Toast.makeText(getApplicationContext(),
		    getApplicationContext().getResources().getString(R.string.http_exception),
		    Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onHttpError(int responseCode, String statusMessage) {
	    Toast.makeText(getApplicationContext(),
		    getApplicationContext().getResources().getString(R.string.http_error),
		    Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onParserException(int responseCode, Throwable exception) {
	    Toast.makeText(getApplicationContext(),
		    getApplicationContext().getResources().getString(R.string.parsing_exception),
		    Toast.LENGTH_SHORT).show();
	}

    }
}
