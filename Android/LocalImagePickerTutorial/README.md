Introduction
====

LocalImageLoaderTutorial is a tutorial project that shows how to use the GCMultiImagePicker component. It contains Chute SDK library as well as GCMultiImagePicker library. 
This tutorial demonstrates how to make GCLocalAssetCollection of assets. Asset represents any photo managed by Chute. This app searches for images on the device, displays the images in a grid and creates GCLocalAssetCollection out of the selected images in the grid.

![IMG_LocalImagePickerTutorial1](https://github.com/chute/photo-picker-plus/raw/master/Android/PhotoPickerPlusTutorial/screenshots/IMG_LocalImagePickerTutorial1.png)

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/ProjectSetup](https://github.com/chute/chute-tutorials/tree/master/Android/ProjectSetup) for a complete guide on how to setup the chute SDK.
  
* Add the GCMultiImagePicker component to your project by either copying all the resources and source code or by adding it as an Android Library project.
  GCMultiImagePicker component can be found and downloaded at [https://github.com/chute/Chute-Android-Kitchen-Sink/tree/master/Components/MultiImagePicker](https://github.com/chute/Chute-Android-Kitchen-Sink/tree/master/Components/MultiImagePicker).

* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
         <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".app.LocalImagePickerTutorialApp"
        android:theme="@android:style/Theme.Light.NoTitleBar" >
        <activity
            android:label="@string/app_name"
            android:name=".app.LocalImagePickerTutorialActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:screenOrientation="portrait" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name="com.chute.android.multiimagepicker.app.MultiImagePickerActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:screenOrientation="portrait" >
            </activity>
         </application>
    ```

Usage
====

##LocalImagePickerTutorialApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
LocalImagePickerTutorialApp can extend MultiImagePickerApp like shown in this tutorial:

<pre><code>
public class LocalImagePickerTutorialApp extends MultiImagePickerApp {

}
</code></pre>

This way the developer can use his own methods and code inside the Application class. 

If the developer decides to extend the Application class instead of MultiImagePickerApp he must copy the all the code below:

<pre><code>
public class LocalImagePickerTutorialApp extends Application {
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

LocalImagePickerTutorialApp can also be neglected by registering MultiImagePickerApp into the manifest instead of LocalImagePickerTutoiralApp if the developer doesn't have the need for extending the Application class.
 
##LocalImagePickerTutorialActivity.java 
This class is an Activity class that contains a "Select Photos" button. When the button is clicked, ChoosePhotosActivityIntentWrapper starts MultiImagePickerActivity. ChoosePhotosActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.

<pre><code>
@Override
	    public void onClick(View v) {
		ChoosePhotosActivityIntentWrapper wrapper = new ChoosePhotosActivityIntentWrapper(this);
		wrapper.startActivityForResult(this,
			ChoosePhotosActivityIntentWrapper.ACTIVITY_FOR_RESULT_KEY);
	    }
</code></pre>    

MultiImagePickerActivity contains GridView that displays device images. You can select one or multiple images. After selecting the desired images, when "Ok" button gets clicked, a result is returned to the activity that started the component where toast message appears showing the number of the selected images.

<pre><code>
@Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ChoosePhotosActivityIntentWrapper.ACTIVITY_FOR_RESULT_KEY) {
		    if (resultCode == RESULT_OK) {
			ChoosePhotosActivityIntentWrapper wrapper = new ChoosePhotosActivityIntentWrapper(
				data);
			GCLocalAssetCollection localAssetCollection = makeGCLocalAssetCollection(wrapper
				.getAssetPathList());
			int photosSelected = localAssetCollection.size();
			Toast.makeText(getApplicationContext(), photosSelected + " photos selected!",
				Toast.LENGTH_SHORT).show();
		    } else {
			Toast.makeText(getApplicationContext(), "No photos selected", Toast.LENGTH_SHORT)
				.show();
		    }
		}
	    }
</code></pre>

ChoosePhotosActivityIntentWrapper encapsulates a list of selected images paths, from which GCLocalAssetCollection can be created:

<pre><code>
 public GCLocalAssetCollection makeGCLocalAssetCollection(ArrayList<String> gridSelectedFilePath) {
		GCLocalAssetCollection collection = new GCLocalAssetCollection();
		GCLocalAssetModel model = new GCLocalAssetModel();
		for (String filePath : gridSelectedFilePath) {
		    model.setFile(filePath);
		    collection.add(model);
		}
		return collection;
	    }
</code></pre>	     	    
  