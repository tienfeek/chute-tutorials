Introduction
====

Join Chute Tutorial is a tutorial project that shows how to join a chute and how to search for chutes. It contains Chute SDK Library and is targeted towards android developers who want to make their applications social. 


Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
         <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".app.JoinChuteTutorialApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".app.JoinChuteTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".app.SearchChutesActivity"></activity>
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
       </application>
    ```
    
Usage
====

##JoinChuteTutorialApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the tutorial.

##JoinChuteTutorialActivity.java
This class is an Activity class that contains two buttons. When "Chute Shortcut" button is clicked, GCMembership.join() AsyncTask is started that tries to join a specific chute using the chutes shortcut.
If the callback succeeds, toast message appears indicating that the chute has been joined. 
Chutes can also require a password that can be added in GCMembership.join().
<pre><code>
private final class ChuteShortcutClickListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			GCMembership.join(getApplicationContext(), SHORTCUT, null,
					new GCMembershipJoinParser(), new GCJoinChuteCallback())
					.executeAsync();
		}

	}
</code></pre>
	
When "Search for Domain" button is clicked, SearchChutesActivityIntentWrapper starts SearchChutesActivity. SearchChutesActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.
<pre><code>
private final class ChuteDomainClickListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final SearchChutesActivityIntentWrapper wrapper = new SearchChutesActivityIntentWrapper(
					JoinChuteTutorialActivity.this);
			wrapper.setShortcut(SHORTCUT);
			wrapper.setDomain(DOMAIN);
			wrapper.startActivity(JoinChuteTutorialActivity.this);
		}

	}
</code></pre>

##SearchChutesActivity.java
This Activity class contains a list of chutes. Using a domain name, GCChutes.search() AsyncTask is started which searches for chutes from a specific URL.
<pre><code>
GCChutes.search(getApplicationContext(), wrapper.getDomain(),
				new SearchChutesCallback()).executeAsync();
</code></pre>	

In the onSuccess() method of GCChutes.search() callback, GCChuteCollection is returned, which is passed to the adapter that fills the ListView with chutes.
When chute is clicked, GCMembership.join() callback is executed, for joining the selected chute.

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
				