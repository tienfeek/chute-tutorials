Introduction
====

Create Chute Tutorial is a tutorial project that shows how to create a basic chute, chute with password and chute with different permissions. This tutorial contains Chute SDK Library. 

![image1](https://github.com/chute/chute-tutorials/raw/master/Android/CreateChuteTutorial/screenshots/1.png)![image2](https://github.com/chute/chute-tutorials/raw/master/Android/CreateChuteTutorial/screenshots/2.png)![image3](https://github.com/chute/chute-tutorials/raw/master/Android/CreateChuteTutorial/screenshots/3.png)![image4](https://github.com/chute/chute-tutorials/raw/master/Android/CreateChuteTutorial/screenshots/4.png)

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
          <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".app.CreateChuteApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".app.CreateChuteTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".app.ChuteBasicActivity" />
        <activity android:name=".app.ChutePasswordActivity" />
        <activity android:name=".app.ChutePermissionsActivity" />
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
        </application>
    ```
    
Usage
====

##CreateChuteApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the tutorial.

##CreateChuteTutorialActivity.java
This Activity class contains three buttons: "Basic Chute" button, "Chute with Password" button and "Chute with Different Permissions" button.
When a button is clicked, ChuteActivityIntentWrapper starts ChuteActivity. ChuteActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.
<pre><code>
private final class ChuteBasicClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final ChuteActivityIntentWrapper wrapper = new ChuteActivityIntentWrapper(
					CreateChuteTutorialActivity.this);
			wrapper.setChuteName(Constants.BASIC_CHUTE_NAME);
			wrapper.setChuteFlag(0);
			wrapper.startActivity(CreateChuteTutorialActivity.this);
		}

	}
</code></pre>

##ChuteActivity.java
This Activity class contains TextView representing the name of the chute to be created, TextView representing the password of the chute, TextView for setting permissions to add photos, TextView for setting permissions to add members and TextView for setting permissions to add comments. 
Different views are filled for different kinds of chutes depending on the flag in the wrapper that indicates which chute was selected.
<code>GCChutes.createChute(Context context, GCChuteModel chuteModel,GCHttpCallback<GCChuteModel> callback)</code> AsyncTask is started that tries to create a chute using GCChuteModel. In the <code>onSuccess(GCChuteModel responseData)</code> method of <code>GCChutes.createChute(Context context, GCChuteModel chuteModel,GCHttpCallback<GCChuteModel> callback)</code> callback, GCChuteModel is returned.
If the callback succeeds, toast message appears indicating that a chute has been created. 	
<pre><code>
GCChutes.createChute(getApplicationContext(), chute,
				new CreateChuteCallback()).executeAsync(); 
				}
private final class CreateChuteCallback implements
			GCHttpCallback<GCChuteModel> {

		@Override
		public void onSuccess(GCChuteModel responseData) {
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
</code></pre>


##Different permissions on chutes

 Visibility = {
    :private  => 0,
    :members  => 1,
    :public   => 2,
    :friends  => 3
  }

  Permissions = {
    :me       => 0,
    :members  => 1,
    :public   => 2,
    :friends  => 3,
    :password => 4
  }


##Request execution and callback

 Every request can be either:
-synchronous (it executes in the same thread as the <code>execute()</code> method was called
-asynchronous (it executes in the Background and it is started by calling <code>executeAsync()</code>);

 Every request can accept a custom response parser or use the default parser for each request type and a suitable callback which will return an object or a collection depending on the response type
 The callback has 4 possible outcomes:

<pre><code>
	// returns the parsed response according to the parsers return type.
	
	public void onSuccess(T responseData); 
    
	// it returns an object that will contain the request parameters, the URL, the headers and the Request Type (GET, POST, PUT, DELETE)
	// this happens if there was a timeout and the request didn't reach the server (usually due to connectivity issues)
    
	public void onHttpException(GCHttpRequestParameters params, Throwable exception); 
	
	// this happens when the server didn't process the result correctly, it returns a HTTP Status code and an error message
    
	public void onHttpError(int responseCode, String statusMessage);
	
	// This happens when the parser didn't successfully parse the response string, usually this requires adjustments on the client side and it is not recoverable by retries
	
	public void onParserException(int responseCode, Throwable exception);
</code></pre>
				