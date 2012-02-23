Introduction
====

Image Sharer Tutorial is a tutorial project that shows how to use the Image Sharer component. It contains Chute SDK library as well as Image Sharer library. 
This tutorial demonstrates how to share a Chute or Asset with Facebook, Twitter or via Email.

![image1](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Sharer%20Tutorial/screenshots/1.png)![image2](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Sharer%20Tutorial/screenshots/2.png)![image3](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Sharer%20Tutorial/screenshots/3.png)![image4](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Sharer%20Tutorial/screenshots/4.png)![image5](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Sharer%20Tutorial/screenshots/5.png)

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* Add the Image Sharer component to your project by either copying all the resources and source code or by adding it as an Android Library project.
  Image Sharer component can be found and downloaded at [https://github.com/chute/chute-android-components/tree/master/Image%20Sharer](https://github.com/chute/chute-android-components/tree/master/Image%20Sharer).

* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
         <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".ShareViewTutorialApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Light.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".ShareViewTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.chute.android.gcshareview.app.ShareActivity"
            android:screenOrientation="portrait" />
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
        </application>
    ```

Usage
====

##ShareViewTutorialApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
ShareViewTutorialApp can extend GCShareViewApp like shown in this tutorial:

<pre><code>
public class ShareViewTutorialApp extends GCShareViewApp {

}
</code></pre>

This way the developer can use his own methods and code inside the Application class. 

If the developer decides to extend the Application class instead of GCShareViewApp he must copy the all the code below:

<pre><code>
public class ShareViewTutorialApp extends Application {
    private static ImageLoader createImageLoader(Context context) {
	ImageLoader imageLoader = new ImageLoader(context, R.drawable.placeholder);
	imageLoader.setRequiredSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		75, context.getResources().getDisplayMetrics()));
	return imageLoader;
    }

    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
	super.onCreate();
	mImageLoader = createImageLoader(this);
    }

    @Override
    public Object getSystemService(String name) {
	if (ImageLoader.IMAGE_LOADER_SERVICE.equals(name)) {
	    return mImageLoader;
	} else {
	    return super.getSystemService(name);
	}
    }

}
</code></pre>

ShareViewTutorialApp can also be neglected by registering GCSahreViewApp into the manifest instead of ShareViewTutoiralApp if the developer doesn't have the need for extending the Application class.
 
##ShareViewTutorialActivity.java
This class is an Activity class. It consists of a button. When the button is clicked, ShareActivityIntentWrapper starts ShareActivity. ShareActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.

<pre><code>
private final class OnShareClickedListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			ShareActivityIntentWrapper wrapper = new ShareActivityIntentWrapper(GCShareViewActivity.this);
			wrapper.setChuteId(Constants.CHUTE_ID);
			wrapper.setChuteName(Constants.CHUTE_NAME);
			wrapper.setChuteShortcut(Constants.CHUTE_SHORTCUT);
			wrapper.startActivity(GCShareViewActivity.this);
		}
    	
    }
</code></pre>

##ShareActivity.java
This Activity class consists of a dialog view containing three buttons. 
"Share via Facebook" and "Share via Twitter" buttons start a WebView for sharing to those services with a present message.

<pre><code>
@Override
    protected Dialog onCreateDialog(int id) {
	switch (id) {
	case DIALOG_TWITTER:
	    return new DialogShareTwitter(this, wrapper.getChuteShortcut());
	case DIAlOG_FACEBOOK:
	    return new DialogShareFacebook(this, wrapper.getChuteShortcut());
	}
	return super.onCreateDialog(id);
    }
</code></pre>
    
"Share via Email" button displays a screen showing a standard email sheet with a preset message and subject that are editable.
  
<pre><code>
private class OnEmailClickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
	    String body = getString(R.string.share_email_body);
	    body = String.format(body, wrapper.getChuteName(),
		    AppUtil.generateShareURLfromCode(wrapper.getChuteShortcut()));
	    IntentUtil.sendEmail(v.getContext(), null, null, body);
	}
    }
</code></pre>        
  