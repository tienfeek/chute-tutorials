
Introduction
====

This demo project shows how to use the basic functionalities of the SDK. It also demonstrates 
how to use Authentication tutorial in your project.


Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at 
  [https://github.com/chute/chute-tutorials/tree/master/Android/ProjectSetup](https://github.com/chute/chute-tutorials/tree/master/Android/ProjectSetup).
  
* Follow the Authentication tutorial that can be found and downloaded at 
  [https://github.com/chute/chute-tutorials/tree/master/Android/Authentication](https://github.com/chute/chute-tutorials/tree/master/Android/Authentication).

* Select Project -> Properties -> Android and add Authentication as a library project.

          ![IMMG_Properties](https://github.com/chute/chute-tutorials/raw/master/Android/BasicFunctionalitiesDemo/screenshots/IMMG_Properties.PNG)
  
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
    
    
LoginActivity  
====         
  
LoginActivity needs to extend BaseLoginActivity from Authentication tutorial. It overrides launchMainAppActivity()
method which is responsible for handling the authentication process and starting the BasicFunctionalitiesDemoActivity.

<pre><code>
       @Override
        public void launchMainAppActivity() {
	    // This method will be responsible for handling the authentication
	    // success or if the user was previously authenticated successfully.
	    final Intent intent = new Intent(getApplicationContext(),
		BasicFunctionalitiesDemoActivity.class);
	    startActivity(intent);
	    LoginActivity.this.finish();
        }
</code></pre>  


BasicFunctionalitiesDemoActivity
====

This activity demonstrates how to use the basic SDK methods.  
