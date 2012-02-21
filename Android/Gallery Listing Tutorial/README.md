Introduction
====

Gallery Listing Tutorial is a tutorial project that shows how to use the Gallery Listing component. It contains Chute SDK library as well as Gallery Listing library.
This tutorial demonstrates a list of chutes. Chute represents a container for Assets. An Asset is any photo managed by Chute. This app takes the current user ID and displays GCChuteCollection. 

![image1] 

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* Add the Gallery Listing component to your project by either copying all the resources and source code or by adding it as an Android Library project.
  Gallery Listing component can be found and downloaded at [https://github.com/chute/Chute-Android-Kitchen-Sink/tree/master/Components/Multi-Image%20Picker](https://github.com/chute/Chute-Android-Kitchen-Sink/tree/master/Components/Multi-Image%20Picker).

* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
         <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".GalleryListingTutorialApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Black.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".GalleryListingTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.chute.android.gcchutelisting.app.GalleryListingActivity"
            android:screenOrientation="portrait" />
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
     </application>
    ```
    
Usage
====

##GalleryListingTutorialApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
GalleryListingTutorialApp can extend GalleryListingApp like shown in this tutorial:

<pre><code>
public class GalleryListingTutorialApp extends GalleryListingApp {

}
</code></pre>

This way the developer can use his own methods and code inside the Application class. 

If the developer decides to extend the Application class instead of GalleryListingApp he must copy the all the code below:

<pre><code>
public class GalleryListingTutorialApp extends Application {
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

GalleryListingTutorialApp can also be neglected by registering GalleryListingApp into the manifest instead of GalleryListingTutoiralApp if the developer doesn't have the need for extending the Application class.
   
##GalleryListingTutorialActivity.java
This class is an Activity class that contains a "Show Gallery List" button. When the button is clicked, GalleryListingActivityIntentWrapper starts GalleryListingActivity. GalleryListingActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.

<pre><code> 
 @Override
    public void onClick(View v) {
	final GalleryListingActivityIntentWrapper wrapper = new GalleryListingActivityIntentWrapper(this);
	wrapper.startActivity(this);
    }
</code></pre>  

GalleryListingActivity contains ListView which is filled with chutes and an AyncTask that gets the chutes for the current user ID.
<pre><code>
GCUser.userChutes(getApplicationContext(), GCConstants.CURRENT_USER_ID,
		new GCChuteCollectionCallback()).executeAsync();
</code></pre>

GCUser.userChutes() has GCHttpCallback<GCChuteCollection> callback which returns GCChuteCollection as a result in its onSuccess() method.
The GCChuteCollection is passed to the adapter which starts loading the ListView after the AsyncTask is finished.

<pre><code>
private final class GCChuteCollectionCallback implements GCHttpCallback<GCChuteCollection> {

	@Override
	public void onSuccess(GCChuteCollection responseData) {
	    adapter = new GalleryListingAdapter(GalleryListingActivity.this, responseData);
	    listView.setAdapter(adapter);
	}

	@Override
	public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
	    Toast.makeText(getApplicationContext(), getString(R.string.http_exception),
		    Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onHttpError(int responseCode, String statusMessage) {
	    Toast.makeText(getApplicationContext(), getString(R.string.http_error),
		    Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onParserException(int responseCode, Throwable exception) {
	    Toast.makeText(getApplicationContext(), getString(R.string.parsing_exception),
		    Toast.LENGTH_SHORT).show();
	}
    }
</code></pre>   