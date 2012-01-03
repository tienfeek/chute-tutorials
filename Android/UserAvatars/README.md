
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
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 ```
- Register the activities into the manifest:
  
    ```
    <activity
            android:label="@string/app_name"
            android:name=".app.GCUserAvatarActivity"
            android:screenOrientation="portrait" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".app.SingleImagePickerActivity"
            android:screenOrientation="portrait" />
        <activity android:name=".app.DisplayAvatarActivity"
            android:screenOrientation="portrait" >
            </activity>
            <activity
            android:name=".imagemanipulation.CropImage"
            android:process=":CropImage"
            android:configChanges="orientation|keyboardHidden"
            android:label="@string/crop_label">
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
            Intent intent = new Intent(SingleImagePickerActivity.this,
					CropImage.class);
			intent.setData(Uri.fromFile(mFile));
			intent.putExtra("outputX", width);
			intent.putExtra("outputY", height);
			intent.putExtra("aspectX", width);
			intent.putExtra("aspectY", height);
			intent.putExtra("scale", true);
			intent.putExtra("noFaceDetection", true);
			intent.putExtra("image-path", imagePath);
			intent.putExtra("output",
					Uri.parse("file:/" + mFile.getAbsolutePath()));
			startActivityForResult(intent, REQUEST_CROP_IMAGE); 
</code></pre>

## CropImage Activity
This Activity consists of ImageView and two buttons "Save" and "Discard". The previously selected image from the picker is displayed in the ImageView. 
On top of the image, this activity draws a rectangle that can change its size and shows which area of the image is about to be cropped.
When "Discard" button is clicked, the SingleImagePicker activity is shown again, and when "Save" button is clicked, the cropped bitmap is saved, uploaded in a chute
and displayed on the screen.
<pre><code> 
           try {
				outputStream = mContentResolver.openOutputStream(mSaveUri);
				if (outputStream != null) {
					croppedImage.compress(mOutputFormat, 75, outputStream);
				}
			} catch (IOException ex) {
				Log.e(TAG, "Cannot open file: " + mSaveUri, ex);
			} finally {
				Util.closeSilently(outputStream);
			}
			try {
				uploadPhoto(mImagePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.putExtra("image", croppedImage);
			intent.putExtra("imagePath", mImagePath);
			setResult(RESULT_OK, intent);
</code></pre>

## DisplayAvatarActivity
This activity displays the cropped image on the screen. DisplayAvatarActivity is called using the DisplayAvatarActivityIntentWrapper
class which wraps the parameters needed for the intent. It stores the image path and cropped bitmap. 
<pre><code>	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CROP_IMAGE) {
			if (resultCode == RESULT_OK) {
				String imagePath = data.getStringExtra("imagePath");
				Bitmap croppedImage = data.getParcelableExtra("image");
				DisplayAvatarActivityIntentWrapper wrapper = new DisplayAvatarActivityIntentWrapper(
						SingleImagePickerActivity.this);
				wrapper.setBitmap(croppedImage);
				wrapper.setImagePath(imagePath);
				wrapper.startActivity(SingleImagePickerActivity.this);	
				}
</code></pre>
The ImageView is filled with the cropped bitmap using the ImageLoader.
<pre><code>	
        ImageLoader loader = ImageLoader.get(getApplicationContext());
		ImageView image = (ImageView) findViewById(R.id.imageView);
		loader.displayImage(imagePath, image);					
</code></pre>
New GCUserModel is created and image path is set as avatar URL.
<pre><code>
        GCUserModel user = new GCUserModel();
		user.setAvatarURL(imagePath); 
</code></pre>

## SingleImagePickerAdapter
The Adapter is called using a Cursor object which searches the images on the device:
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