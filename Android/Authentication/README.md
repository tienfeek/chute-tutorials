
Introduction
====

This tutorial will help you successfully log into your Facebook or Twitter account.


Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at 
  [example link](https://github.com/chute/chute-tutorials/tree/master/Android/ProjectSetup).
  
* Register the activity into the AndroidManifest.xml file
 ```
    <activity
            android:name=".LoginActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
 ```
 

Login Activity  
====

In order to successfully log into Facebook or Twitter, you need to set up the client ID 
and secret in the LoginActivity:

<pre><code>
    private final class OnLoginClickListener implements OnClickListener {
	@Override
	public void onClick(final View v) {
	    final AccountType accountType = (AccountType) v.getTag();
	    GCAccount.getInstance(getApplicationContext()).startAuthenticationActivity(
		    LoginActivity.this, accountType, "replace with profile permissions scope",
		    "replace with your predefined callback url", "replace with client id",
		    "replace with client secret");
	}
    }
</code></pre> 

If the login fails, some error has occurred: 
- HTTP Exception, shows that the login failed due to Internet connection issues.
- HTTP Error, shows that the login failed due to sever problems.
- Parser Exception, shows that the login failed due to invalid response format. 
       