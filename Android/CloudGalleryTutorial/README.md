Introduction
====

Cloud Gallery Tutorial is a tutorial project that shows how to use the Cloud Gallery component. It contains Chute SDK library as well as Cloud Gallery library. 
This tutorial demonstrates how to display Assets in a gallery.

![image1](https://github.com/chute/chute-tutorials/raw/master/Android/CloudGalleryTutorial/screenshots/1.png)![image2](https://github.com/chute/chute-tutorials/raw/master/Android/CloudGalleryTutorial/screenshots/2.png)

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* Add the Cloud Gallery component to your project by either copying all the resources and source code or by adding it as an Android Library project.
  Cloud Gallery component can be found and downloaded at [https://github.com/chute/chute-android-components/tree/master/Cloud%20Gallery](https://github.com/chute/chute-android-components/tree/master/Cloud%20Gallery).

* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
         <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".CloudGalleryTutorialApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Black.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".CloudGalleryTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".GalleryActivity" />
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
        </application>
    ```

Usage
====

##CloudGalleryTutorialApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
CloudGalleryTutorialApp can extend CloudGalleryApp like shown in this tutorial:

<pre><code>
public class CloudGalleryTutorialApp extends CloudGalleryApp {

}
</code></pre>

This way the developer can use his own methods and code inside the Application class. 

If the developer decides to extend the Application class instead of CloudGalleryApp he must copy the all the code below:

<pre><code>
public class CloudGalleryTutorialApp extends Application {
   private static ImageLoader createImageLoader(Context context) {
		ImageLoader imageLoader = new ImageLoader(context, R.drawable.placeholder_image_small);
		imageLoader.setDefaultImageSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 75, context.getResources()
						.getDisplayMetrics()));
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

CloudGalleryTutorialApp can also be neglected by registering CloudGalleryApp into the manifest instead of CloudGalleryTutoiralApp if the developer doesn't have the need for extending the Application class.

##CloudGalleryTutorialActivity.java 
This Activity class contains a "Start Cloud Gallery" button. When the button is clicked, GalleryActivity is started.
<pre><code>
@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(),
					GalleryActivity.class);
			startActivity(intent);
		}
</code></pre>  

##GalleryActivity.java
This activity class contains a GalleryViewFlipper view from the Cloud Gallery component.
Using a Chute ID, the GCChutes.Resources.assets(Context context, String id, GCHttpCallback<GCAssetCollection> callback) AsynTask is being executed:
<pre><code>
GCChutes.Resources.assets(getApplicationContext(), CHUTE_ID,
				new AssetCollectionCallback()).executeAsync();   
</code></pre>

<code>GCChutes.Resources.assets(Context context, String id, GCHttpCallback<GCAssetCollection> callback)</code> has GCHttpCallback<GCAssetCollection> callback which returns GCAssetCollection as a result in its <code>onSuccess(GCAssetCollection responseData)</code> method.
The GCAssetCollection is passed to the GalleryViewFlipper which starts loading the Asset collection after the AsyncTask is finished.
<pre><code>
private final class AssetCollectionCallback implements
			GCHttpCallback<GCAssetCollection> {

		@Override
		public void onSuccess(GCAssetCollection responseData) {
			gallery.setAssetCollection(responseData);
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {

		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
		}

	}
</code></pre>

The GalleryViewFlipper contains GalleryCallback interface which can be used for easy manipulation with assets.
<pre><code>
private final class NewGalleryCallback implements GalleryCallback {

		@Override
		public void triggered(GestureEvent event) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPhotoChanged(int index, GCAssetModel asset) {
		}

		@Override
		public void onPhotoChangeError(PhotoChangeErrorType error) {
			// TODO Auto-generated method stub

		}

	}
</code></pre>
<code>triggered(GestureEvent event)</code> method can perform actions on different gestures using the GestureEvent object. The gestures that are listed are: single tap, double tap, swipe left, swipe right, swipe top and swipe bottom.
<code>onPhotoChanged(int index, GCAssetModel asset)</code> method can be used for various actions that include asset position and GCAssetModel.
<code>onPhotoChangeError(PhotoChangeErrorType error)</code> method can be used for handling errors such as: no previous item, no next item and general error.  

## Request execution and callback

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