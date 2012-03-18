package com.chute.android.tutorials.basicfunctionalities;

import android.app.Activity;
import android.os.Bundle;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.utils.GCConstants;

public class BasicFunctionalitiesDemoActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	// Now since the authentication has passed successfully we can use the
	// sdk methods

	/*Every SDK method that is made available is accessed through one of the static classes:
	GCChutes
	GCAssets
	GCComments
	GCHearts
	GCMembership
	etc...
	Each of these classes are located inside specific packages inside the api package in the chute sdk project.
	
	Each method returns an object implementing the HTTP request interface which has two methods:
	executeAsync() allows the developer to execute the request asynchronously; 
	execute() allows the developer to execute the request in the current thread that the call was made.
	
	Each call can be executed with a default parser that returns a chute-specific object or a custom one by adding a custom object implementing GCHttpResponseParser.
	
	The callback has 4 methods. One of those methods will be called according to the specific outcome of the request.
	
	*
	*/

	// EXAMPLE: fetch a list of all the chutes for the current user

	GCChutes.all(getApplicationContext(), GCConstants.CURRENT_USER_ID,
		new GCChutesAllCallback()).executeAsync();
    }

    private final class GCChutesAllCallback implements GCHttpCallback<GCChuteCollection> {
	@Override
	public void onSuccess(GCChuteCollection responseData) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void onParserException(int responseCode, Throwable exception) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onHttpError(int responseCode, String statusMessage) {
	    // TODO Auto-generated method stub

	}
    }
}