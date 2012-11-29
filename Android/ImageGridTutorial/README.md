Introduction
====

Image Grid Tutorial is a tutorial project that shows how to use the Image Grid component. It contains Chute SDK library as well as Image Grid library.
This tutorial demonstrates a grid of assets. It takes a random chute ID and displays GCAssetCollection for the chosen chute.  

![image1](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Grid%20Tutorial/screenshots/1.png)![image2](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Grid%20Tutorial/screenshots/2.png) 

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* Add the Image Grid component to your project by either copying all the resources and source code or by adding it as an Android Library project.
  Image Grid component can be found and downloaded at [https://github.com/chute/chute-android-components/tree/master/Image%20Grid](https://github.com/chute/chute-android-components/tree/master/Image%20Grid).

* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
         <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".app.ImageGridTutorialApp"
        android:theme="@android:style/Theme.Light.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".app.ImageGridTutorialActivity"
            android:screenOrientation="portrait" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.chute.android.imagegrid.app.ImageGridActivity"
            android:screenOrientation="portrait" >
        </activity>
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
     </application>
    ```
    
Usage
====

##ImageGridTutorialApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
ImageGridTutorialApp can extend ImageGridApp like shown in this tutorial:

<pre><code>
public class ImageGridTutorialApp extends ImageGridApp {

}
</code></pre>

This way the developer can use his own methods and code inside the Application class. 

If the developer decides to extend the Application class instead of ImageGridApp he must copy the all the code below:

<pre><code>
public class ImageGridTutorialApp extends Application {
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

ImageGridTutorialApp can also be neglected by registering ImageGridApp into the manifest instead of ImageGridTutoiralApp if the developer doesn't have the need for extending the Application class.
    
##ImageGridTutorialActivity.java
This class is an Activity class that contains a "Show Assets" button. When the button is clicked, ImageGridIntentWrapper starts ImageGridActivity. ImageGridIntentWrapper is a wrapper class that wraps the parameters needed for the intent.

<pre><code> 
@Override
        public void onClick(View v) {
    	ImageGridIntentWrapper wrapper = new ImageGridIntentWrapper(this);
    	wrapper.setChuteID("1946"); // Replace with chute id
    	wrapper.startActivity(this);
        }
</code></pre>  

ImageGridActivity contains GridView which is filled with assets and an AyncTask that gets the assets for a specific chute ID.
In order to get the assets, first a new GCChuteModel must be created:

<pre><code>
final GCChuteModel chute = new GCChuteModel();
	final ImageGridIntentWrapper wrapper = new ImageGridIntentWrapper(getIntent());
	chute.setId(wrapper.getChuteId());
</code></pre>

The newly created chute is given a chute ID and <code>GCChutes.assets(Context context, String id, GCHttpCallback<GCAssetCollection> callback)</code> AsyncTask is triggered:
<pre><code>
chute.assets(getApplicationContext(), new AssetCollectionCallback()).executeAsync();
</code></pre>

<code>GCChutes.assets(Context context, String id, GCHttpCallback<GCAssetCollection> callback)</code> has GCHttpCallback<GCChuteModel> callback which returns GCChuteModel as a result in its <code>onSuccess(GCChuteModel responseData)</code> method.
The GCAssetCollection is retrieved from the GCChuteModel and passed to the adapter which starts loading the GridView after the AsyncTask is finished. 
<pre><code>
// Callback which returns a collection of assets for a given chuteId
    private final class AssetCollectionCallback implements GCHttpCallback<GCChuteModel> {

	@Override
	public void onSuccess(GCChuteModel responseData) {
	    adapter = new AssetCollectionAdapter(ImageGridActivity.this,
		    responseData.assetCollection);
	    grid.setAdapter(adapter);
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
				   	          