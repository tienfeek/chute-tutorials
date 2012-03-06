Introduction
====

Create Chute Tutorial is a tutorial project that shows how to create a basic chute, chute with a password and chute with different permissions. It contains Chute SDK Library and is targeted towards android developers who want to make their applications social. 

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
This class is an Activity class that contains three buttons. 
When "Basic Chute" button is clicked, ChuteBasicActivityIntentWrapper starts ChuteBasicActivity. ChuteBasicActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.
<pre><code>
private final class ChuteBasicClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final ChuteBasicActivityIntentWrapper wrapper = new ChuteBasicActivityIntentWrapper(
					CreateChuteTutorialActivity.this);
			wrapper.setChuteName(Constants.BASIC_CHUTE_NAME);
			wrapper.startActivity(CreateChuteTutorialActivity.this);
		}

	}
</code></pre>
When "Chute with Password" button is clicked, ChutePasswordActivityIntentWrapper starts ChutePasswordActivity.
<pre><code>
private final class ChutePasswordClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final ChutePasswordActivityIntentWrapper wrapper = new ChutePasswordActivityIntentWrapper(
					CreateChuteTutorialActivity.this);
			wrapper.setChuteName(Constants.PASSWORD_CHUTE_NAME);
			wrapper.setChutePassword(Constants.PASSWORD);
			wrapper.startActivity(CreateChuteTutorialActivity.this);
		}

	}
</code></pre>
When "Chute with Different Permissions" button is clicked, ChutePermissionsActivityIntentWrapper starts ChutePermissionsActivity.
<pre><code>
private final class ChutePermissionsClickListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			ChutePermissionsActivityIntentWrapper wrapper = new ChutePermissionsActivityIntentWrapper(
					CreateChuteTutorialActivity.this);
			wrapper.setChuteName(Constants.PERMISSIONS_CHUTE_NAME);
			wrapper.setChutePassword(Constants.PASSWORD);
			wrapper.startActivity(CreateChuteTutorialActivity.this);
		}

	}
</code></pre>

##ChuteBasicActivity.java
This Activity class contains TextView representing the name of the chute to be created. 
<code>GCChutes.createChute()</code> AsyncTask is started that tries to create a chute using GCChuteModel. In the <code>onSuccess()</code> method of <code>GCChutes.createChute()</code> callback, GCChuteModel is returned, which is passed to the 
<code>addChute()</code> method in the ChutesSingleton class which adds the chute in a collection of chutes. If the callback succeeds, toast message appears indicating that a chute has been created. 	
<pre><code>
GCChutes.createChute(getApplicationContext(), chute,
				new BasicChuteCallback()).executeAsync(); 
				}
private final class BasicChuteCallback implements
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
</code></pre>

##ChutePasswordActivity.java
This Activity class contains TextView "name" representing the name of the chute to be created and TextView "password" representing the password of the chute.
The name and password are set to the GCChuteModel which is passed to the <code>GCChutes.createChute()</code> callback that tries to create the given chute.
<pre><code>
GCChutes.createChute(getApplicationContext(), chute,
				new BasicChuteCallback()).executeAsync();
</code></pre>

##ChutePermissionsActivity.java
This Activity class contains TextView "name" representing the name of the chute to be created, TextView "password" representing the password of the chute, TextView "permission photos" representing permission to add photos to the chute,
TextView "permission members" representing permission to add members to the chute and TextView "permission comments" representing permission to add comments to the chute.
The name, password and permissions are set to the GCChuteModel which is passed to the <code>GCChutes.createChute()</code> callback that tries to create the given chute.
<pre><code>
GCChutes.createChute(getApplicationContext(), chute,
				new BasicChuteCallback()).executeAsync();
</code></pre>

								
## Request execution and callback

 Every request can be either:
-synchronous (it executes in the same thread as the <code>execute()</code> method was called
-asynchronous (it executes in the Background and it is started by calling <code>executeAsync()</code>);

 Every request can accept a custom response parser or use the default parser for each request type and a suitable callback which will return an object or a collection depending on the response type
 The callback has 4 possible outcomes

	<pre>
	// returns the parsed response according to the parsers return type.
	
	<code>public void onSuccess(T responseData); </code>
    
	// it returns an object that will contain the request parameters, the URL, the headers and the Request Type (GET, POST, PUT, DELETE)
	// this happens if there was a timeout and the request didn't reach the server (usually due to connectivity issues)
    
	<code>public void onHttpException(GCHttpRequestParameters params, Throwable exception); </code>
	
	// this happens when the server didn't process the result correctly, it returns a HTTP Status code and an error message
    
	<code>public void onHttpError(int responseCode, String statusMessage);</code>
	
	// This happens when the parser didn't successfully parse the response string, usually this requires adjustments on the client side and it is not recoverable by retries
	
	<code>public void onParserException(int responseCode, Throwable exception);</code>
	</pre>
				