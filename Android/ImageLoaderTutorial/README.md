
Introduction
====

ImageLoader tutorial is a tutorial project that shows how to use the SDK ImageLoader. 
This tutorial displays an image from SDcard, URL and Chute photo URL using the SDK ImageLoader.

![IMG_IMAGELOADER1](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Loader%20Tutorial/screenshots/IMG_ImageLoader1.png)![IMG_IMAGELOADER2](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Loader%20Tutorial/screenshots/IMG_ImageLoader2.png)


Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* Register the activities, services and the application class into the AndroidManifest.xml file:

    ```
        <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".app.ImageLoaderTutorialApp" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".app.ImageLoaderTutorialActivity"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
        <activity
            android:name=".app.LoadImageActivity"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Light.NoTitleBar" >
        </activity>
    </application>
    ```


Usage
====

##ImageLoaderTutorialApp.java
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes.
ImageLoaderTutorialApp is where an instance of the ImageLoader is created. 

<pre><code>
private static ImageLoader createImageLoader(Context context) {
		ImageLoader imageLoader = new ImageLoader(context,
				R.drawable.placeholder_image_small);
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
</code></pre>

##ImageLoaderTutorialActivity.java
This class contains three buttons. "Display image from SDcard" button displays an image 
that can be found on the device, "Display image from URL" button displays an image from  
a specific URL and "Display image from Chute URL" button displays an image from URL that
shows chutes.
LoadImageActivityIntentWrapper represents a class that wraps the parameters needed for the intent.
Depending on the file type wrapped in the intent, LoadImageActivity displays the desired image. 
<pre><code>
private final class OnButtonSdcardClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
					ImageLoaderTutorialActivity.this);
			wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_SDCARD);
			wrapper.startActivity(ImageLoaderTutorialActivity.this);
		}

	}
</pre></code>	

##LoadImageActivity.java
This activity class contains an ImageView that displays the image. The image is displayed
using the ImageLoader that is initialized in the <code>onCreate()</code> method in LoadImageActivity class.
<pre><code>
ImageLoader loader = ImageLoader.getLoader(LoadImageActivity.this);
</code></pre>

The ImageLoader displays an image using its <code>displayImage(String url, ImageView imageView)</code> method containing String value representing
the URL as an argument, and ImageView where the image is placed.

If image from SDcard is chosen, String value representing the path of the image is placed as first
argument in the <code>displayImage(String url, ImageView imageView)</code> method. The path is located using Cursor object.
<pre><code>
Cursor cursor = MediaDAO.getMediaPhotos(LoadImageActivity.this);
			if (cursor != null && cursor.moveToFirst()) {
				String path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				loader.displayImage(Uri.fromFile(new File(path)).toString(),
						imageView);
			}
</code></pre>

If image from URL is chosen, the URL is placed as first argument in the <code>displayImage(String url, ImageView imageView)</code> method.
<pre><code>
loader.displayImage("http://www.sun-protection-and-you.com/images/sun-stroke.jpg", imageView);	
</code></pre>

If image from Chute URL is chosen, the Chute URL is placed as first argument in the <code>displayImage(String url, ImageView imageView)</code> method.
<pre><code>
loader.displayImage("http://sharedroll.com/hwcybf", imageView);
</code></pre>							 
  