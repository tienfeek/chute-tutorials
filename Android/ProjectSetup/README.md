
Introduction to Chute
====

This tutorial will help you successfully set up the basic things that are required for
getting started with Chute.

You can download the Chute SDK for android from our github account:

[https://github.com/chute/Chute-SDK](https://github.com/chute/Chute-SDK)

Additionally you can visit [http://developer.getchute.com/](http://developer.getchute.com/) for more information about creating a new developer account and obtaining chute credentials.

Basic SDK Setup
====

* Download the SDK 
* In Eclipse click File -> Import -> Existing Projects into Workspace

![IMG_SDK_1](https://github.com/chute/chute-tutorials/raw/master/Android/Project%20Setup/screenshots/IMG_SDK_1.PNG)

* Browse to the downloaded root directory of the downloaded sdk.
* Check the SDK project and click Finish.

Troubleshooting:
----

* If after importing the project there are errors in the build path try cleaning the project to rebuild the R.java file.
* Make sure the SDK is set as a library project by right clicking the project -> properties -> Android -> Check Is Library

If you are creating a new project with the Chute SDK go over Basic New Project Setup, otherwise skip to the next step in the tutorial.

Basic New Project Setup
====

* Open Eclipse and create new Android project by selecting File->New->Android Project.
* Type the name of the project, it can be anything you like, I'll name it NewProject.

       ![IMG_Project1](https://github.com/chute/chute-tutorials/raw/master/Android/Project%20Setup/screenshots/IMG_Project1.png)
  
* Select build target. I'll use Android 2.1 API Level 7.  
 
       ![IMG_Project2](https://github.com/chute/chute-tutorials/raw/master/Android/Project%20Setup/screenshots/IMG_Project2.png)
  
* Add a package name. The package name I added is: com.android.newproject

       ![IMG_Project3](https://github.com/chute/chute-tutorials/raw/master/Android/Project%20Setup/screenshots/IMG_Project3.png)
  
Adding the SDK library to your project
====

* After successfully creating a new Android project or opening an existing one, the next thing that needs to be done
  is adding the Chute SDK as a library project.
* Chute SDK project can be found and downloaded [here](https://github.com/chute/Chute-SDK). Or visit [http://developer.getchute.com/](http://developer.getchute.com/) for more info.

* Select Project -> Properties -> Android and add ChuteSDK as a library project.

       ![IMG_Project4](https://github.com/chute/chute-tutorials/raw/master/Android/Project%20Setup/screenshots/IMG_Project4.png)
  
    
Android manifest setup
====

* Open the AndroidManifest.xml file 

* Add the following permissions that are required for the Chute SDK:

    ```
        Standard Permission to access the internet
        <uses-permission android:name="android.permission.INTERNET" />
        Permission for caching the images on the SD card
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        The following permissions are used by chute to track the phone id and version to determine the device used for managing the uploads and users
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.GET_ACCOUNTS" />
        Used for the http request service for the long running uploads.
        <uses-permission android:name="android.permission.WAKE_LOCK" />
    ```

Apart from registering your own components in the manifest, you will need to add the following service and activity also:

* Register the GCHttpService from ChuteSDK:

    ```
        <service android:name="com.chute.sdk.api.GCHttpService" >
        </service> 
    ```
 
* Register the GCAuthenticationActivity from ChuteSDK:

    ```
        <activity android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity" >
        </activity> 
    ```
 