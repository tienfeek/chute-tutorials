Introduction
====

Social Gallery Tutorial is a tutorial project that shows how to use the Social Gallery component.
It includes SDK library and Social Gallery library. This tutorial enables browsing photos in a gallery, sharing photos, commenting on photos and marking photos as favorite.

![image1](https://github.com/chute/chute-android-components/raw/master/SocialGalleryTutorial/screenshots/1.png)![image2](https://github.com/chute/chute-android-components/raw/master/SocialGalleryTutorial/screenshots/2.png)![image3](https://github.com/chute/chute-android-components/raw/master/SocialGalleryTutorial/screenshots/3.png)![image4](https://github.com/chute/chute-android-components/raw/master/SocialGalleryTutorial/screenshots/4.png)![image5](https://github.com/chute/chute-android-components/raw/master/SocialGalleryTutorial/screenshots/5.png)

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* Add the Social Gallery component to your project by either copying all the resources and source code or by adding it as an Android Library project.
  Social Gallery component can be found and downloaded at [https://github.com/chute/chute-android-components/tree/master/Social%20Gallery](https://github.com/chute/chute-android-components/tree/master/Social%20Gallery).
    
* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
          <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".SocialGalleryTutorialApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Black.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".SocialGalleryTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name="com.chute.android.socialgallery.app.SocialGalleryActivity" />
        <activity android:name="com.chute.android.imagesharer.app.ShareActivity" />
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
        <activity android:name="com.chute.android.comments.app.PhotoCommentsActivity" />
        </application>
    ```
    
Usage
====

##SocialGalleryTutorialApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
SocialGalleryTutorialApp can extend SocialGalleryApp like shown in this tutorial:

<pre><code>
public class SocialGalleryTutorialApp extends SocialGalleryApp {

}
</code></pre>

This way the developer can use his own methods and code inside the Application class. 

If the developer decides to extend the Application class instead of SocialGalleryApp he must copy the all the code below:

<pre><code>
public class SocialGalleryTutorialApp extends Application {
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

SocialGalleryTutorialApp can also be neglected by registering SocialGalleryApp into the manifest instead of SocialGalleryTutoiralApp if the developer doesn't have the need for extending the Application class.
 
##SocialGalleryTutorialActivity.java
This Activity class consists of a button. When the button is clicked, SocialGalleryActivityIntentWrapper starts SocialGalleryActivity from Social Gallery component. SocialGalleryActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.
<pre><code>
private final class OnStartClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			SocialGalleryActivityIntentWrapper wrapper = new SocialGalleryActivityIntentWrapper(MainActivity.this);
			wrapper.setChuteId(Constants.CHUTE_ID);
			wrapper.setChuteName(Constants.CHUTE_NAME);
			wrapper.setChuteShortcut(Constants.CHUTE_SHORTCUT);
			wrapper.startActivity(MainActivity.this);
		}
		
	}
</code></pre>

##SocialGalleryActivity.java
This Activity class contains a GalleryViewFlipper view from the Cloud Gallery component, ImageButton "share" for sharing the asset, ImageButton "comment" for commenting on the asset and ImageButton "heart" for hearting the asset.
Using a Chute ID which is retrieved from the SocialGalleryActivityIntentWrapper, <code>GCChutes.Resources.assets(Context context, String id, GCHttpCallback<GCAssetCollection> callback)</code> AsynTask is being executed:
<pre><code>
GCChutes.Resources.assets(getApplicationContext(), wrapper.getChuteId(),
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

When "share" button is clicked, the ShareActivityIntentWrapper starts ShareAvtivity which contains a dialog used for sharing the current asset. 
The asset can be shared with Facebook, Twitter or via Email.
<pre><code>
private final class ShareClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			ShareActivityIntentWrapper wrapper = new ShareActivityIntentWrapper(
					SocialGalleryActivity.this);
			wrapper.setChuteId(socialWrapper.getChuteId());
			wrapper.setChuteName(socialWrapper.getChuteName());
			wrapper.setChuteShortcut(socialWrapper.getChuteShortcut());
			wrapper.startActivity(SocialGalleryActivity.this);
		}

	}
</pre></code>

When "comment" button is clicked, the PhotoCommentsActivityIntentWrapper starts PhotoCommentsActivity which contains ListView for displaying comments, EditText for writting a comment, and "Save" button for submitting a comment.
<pre><code>
 final class CommentsClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			PhotoCommentsActivityIntentWrapper wrapper = new PhotoCommentsActivityIntentWrapper(
					SocialGalleryActivity.this);
			wrapper.setChuteId(gallery.getSelectedItem().getId());
			wrapper.setAssetId(socialWrapper.getAssetId());
			wrapper.setChuteName(socialWrapper.getChuteName());
			wrapper.startActivityForResult(SocialGalleryActivity.this,
					Constants.ACTIVITY_FOR_RESULT_KEY);
		}

	}
</code></pre>

When "heart" button is clicked, the asset is marked as favorite in <code>onPhotoChanged(int index, GCAssetModel asset)</code> method from the Cloud Gallery component GalleryCallback interface.
<pre><code>
private final class NewGalleryCallback implements GalleryCallback {

		@Override
		public void triggered(GestureEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPhotoChanged(int index, GCAssetModel asset) {
			heart.markHeartByAssetId(asset.getId());
		}

		@Override
		public void onPhotoChangeError(PhotoChangeErrorType error) {
			// TODO Auto-generated method stub

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
	    