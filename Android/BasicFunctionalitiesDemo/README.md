
Introduction
====

This demo project shows how to use the basic functionalities of the SDK. It also demonstrates 
how to use Authentication tutorial in your project.

![image1](https://github.com/chute/chute-tutorials/raw/master/Android/BasicFunctionalitiesDemo/screenshots/1.png)![image2](https://github.com/chute/chute-tutorials/raw/master/Android/BasicFunctionalitiesDemo/screenshots/2.png)![image3](https://github.com/chute/chute-tutorials/raw/master/Android/BasicFunctionalitiesDemo/screenshots/3.png)![image4](https://github.com/chute/chute-tutorials/raw/master/Android/BasicFunctionalitiesDemo/screenshots/4.png)

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at 
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup).
  
* Follow the Authentication tutorial that can be found and downloaded at 
  [https://github.com/chute/chute-tutorials/tree/master/Android/Authentication](https://github.com/chute/chute-tutorials/tree/master/Android/Authentication).

* Select Project -> Properties -> Android and add Authentication as a library project.

          ![image5](https://github.com/chute/chute-tutorials/raw/master/Android/BasicFunctionalitiesDemo/screenshots/5.png)
  
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
    
    
LoginActivity.java  
====         
  
LoginActivity needs to extend BaseLoginActivity from Authentication tutorial. It overrides <code>launchMainAppActivity()</code>
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


BasicFunctionalitiesDemoActivity.java
====

This activity demonstrates how to use the basic SDK methods.  
