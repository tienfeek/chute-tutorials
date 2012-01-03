
Introduction
====

UserAvatars is an Android application which demonstrates how to create an avatar and upload it into a chute. This app includes Chute SDK library, searches for images on the device, displays the images in a grid view, crops the selected image from the grid, uploads it in a chute and displays it. 


Setup
====

- Create a new Android project or open an existing one.
- Copy the classes and resources into your project.
- Add the required permissions to the manifest:

 ```
  <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
 ```
- Register the activities into the manifest:
  
    ```
       
        <activity
            android:name=".app.UserAvatarActivity"
            android:label="@string/app_name"
            android:screenOrientation="portrait" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".app.SingleImagePickerActivity"
            android:screenOrientation="portrait" />
        <activity
            android:name=".imagemanipulation.CropImage"
            android:configChanges="orientation|keyboardHidden"
            android:label="@string/crop_label"
            android:process=":CropImage" >
        </activity>
    ```
- Register the service into the manifest:

  ```
  <service android:name="com.chute.sdk.api.GCHttpService" />
  ```
  
      
Key Concepts
========

## GCLocalAssetCollection
Collection of GCLocalAssetModel.

## GCLocalAssetModel
Asset model which consists of: name, asset ID, file, asset status and fileMD5.

## GCChuteCollection
Collection of GCChuteModel.

## GCChuteModel
Model containing all the characteristics for a given chute.

## GCUserModel
Model containing all the characteristics for the user: id, name and url where the avatar image is located.


Usage
========

## Starting the Activity
SingleImagePicker Activity is called when the "Choose Photo" button is clicked. SingleImagePickerIntentWrapper is called, which represents a class that wraps the parameters needed for the intent.
<pre><code>
@Override
		public void onClick(View v) {
			SingleImagePickerIntentWrapper wrapper = new SingleImagePickerIntentWrapper(getApplicationContext());
			wrapper.startActivity(UserAvatarActivity.this);
		}
</code></pre>

## SingleImagePicker Activity
This Activity consists of GridView filled with images that can be found on the device. When one of the images is clicked, the intent for launching the CropImage activity is defined which does the image manipulation.
<pre><code>
          if (requestCode == SingleImagePickerIntentWrapper.ACTIVITY_REQUEST_CODE) {
	    final int width = 200;
	    final int height = 200;

	    tempFileForCroppedImage = FileCache.getTempFile(data.getData().getPath());
	    tempFileForCroppedImage.deleteOnExit();
	    Log.d(TAG, tempFileForCroppedImage.getPath());
	    Intent intent = new Intent(this, CropImage.class);
	    intent.setData(data.getData());
	    intent.putExtra("outputX", width);
	    intent.putExtra("outputY", height);
	    intent.putExtra("aspectX", width);
	    intent.putExtra("aspectY", height);
	    intent.putExtra("scale", true);
	    intent.putExtra("noFaceDetection", true);

	    intent.putExtra("output", Uri.fromFile(tempFileForCroppedImage));
	    startActivityForResult(intent, REQUEST_CROP_IMAGE);
	    return;
	}
</code></pre>

## CropImage Activity
This Activity consists of ImageView and two buttons "Save" and "Discard". The previously selected image from the picker is displayed in the ImageView. 
On top of the image, this activity draws a rectangle that can change its size and shows which area of the image is about to be cropped.
When "Save" is clicked, the cropped bitmap is saved to a temporary file, and then uploaded to a chute.
Initialy the croped temporary file is displayed on the screen.
<pre><code> 
if (requestCode == REQUEST_CROP_IMAGE) {
	    String imagePath = data.getStringExtra("imagePath");
	    Bitmap croppedImage = data.getParcelableExtra("image");
	    thumb.setImageBitmap(croppedImage);
	    path.setText(data.getData().toString());
	    uploadPhoto(data.getData().getPath());
	}
</code></pre>

When the upload is finished sucessfully the uploaded image is pulled from the server and displayed on screen using the ImageLoader component.
<pre><code>	
        loader.displayImage(GCUtils.getCustomSizePhotoURL(url, 75, 75), thumb);
</code></pre>

## SingleImagePickerAdapter
The Adapter is called using a Cursor object which contains the images inside the DCIM folder on the sdcard.
<pre><code>
public static Cursor getMediaPhotos(Context context) {
		String[] projection = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA };
		Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String query = MediaStore.Images.Media.DATA + " LIKE \"%DCIM%\"";
		return context.getContentResolver().query(images, projection, query,
				null, null);
	}
</code></pre>		   