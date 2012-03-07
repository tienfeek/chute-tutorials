Introduction
====

Asset Heart Tutorial is a tutorial project that enables browsing photos in a gallery and marking photos as favorite.
It includes SDK library and Cloud Gallery library, and is targeted towards android developers who want to make their applications social. 

![image1](https://github.com/chute/chute-tutorials/raw/master/Android/AssetHeartTutorial/screenshots/1.png)![image2](https://github.com/chute/chute-tutorials/raw/master/Android/AssetHeartTutorial/screenshots/2.png)

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
        android:name=".app.AssetHeartApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Black.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".app.AssetHeartTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
        </application>
    ```
    
Usage
====

##AssetHeartApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
 
##AssetHeartTutorialActivity.java
This Activity class contains a GalleryViewFlipper view from the Cloud Gallery component and ImageButton "heart" for hearting the asset.
Using a Chute ID, <code>GCChutes.Resources.assets()</code> AsynTask is being executed:
<pre><code>
GCChutes.Resources.assets(getApplicationContext(), chuteId,
				new AssetCollectionCallback()).executeAsync();   
</code></pre>

<code>GCChutes.Resources.assets()</code> has <code>GCHttpCallback<GCAssetCollection></code> callback which returns GCAssetCollection as a result in its <code>onSuccess()</code> method.
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

When "heart" button is clicked, the asset is marked as favorite in <code>onPhotoChanged()</code> method from the Cloud Gallery component GalleryCallback interface.
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
	    