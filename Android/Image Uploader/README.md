
Introduction
====

ImageUploader is a tutorial project which gets an image from the assets folder and uploads the image in a chute. It includes Chute SDK library.

![IMGG_Photoupload](https://github.com/chute/chute-tutorials/raw/master/Android/Image%20Uploader/screenshots/IMGG_Photoupload.png)
            
Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at 
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup).
  
* Register the activities into the AndroidManifest.xml file:

    ```
        <activity
            android:label="@string/app_name"
            android:name=".PhotoUploadActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    ```
  
  
Usage
====

## Starting the Activity

The first thing that has to be done is creation of new GCAccount. Random token is set as a password:
<pre><code>
    GCAccount.getInstance(this).setUsername("TestUser");
	GCAccount.getInstance(this).setPassword(
		"3115093a84aa3c4b696f9e5e0356826085e6c2810e55931ccd4d227ba70cfdbb");
</code></pre>  

PhotoUploadActivity consists of "Upload File" button, progress bar and ImageView for displaying the uploaded image.
When "Upload File" button is clicked, new GCLocalAssetModel is created. The image that can be found in the assets folder is copied
on SDCard and set to the GCLocalAssetModel. 
<pre><code>
GCLocalAssetModel asset = new GCLocalAssetModel();
	    try {
		asset.setFile(FileUtil.copyAsset(getApplicationContext(), "droid4.jpg"));
	    } catch (IOException e) {
		Log.w(TAG, "", e);
	    }  
</code></pre> 

New GCLocalAssetCollection is created and the previously created asset is added to the collection.
New GCChuteModel is created with a random chuteId and added to the GCChuteCollection.
<pre><code>
        GCLocalAssetCollection assetCollection = new GCLocalAssetCollection();
	    assetCollection.add(asset);
	    GCChuteModel chuteModel = new GCChuteModel();
	    chuteModel.setId("1183"); // Test ID for a public chute called
	    GCChuteCollection chuteCollection = new GCChuteCollection();
	    chuteCollection.add(chuteModel);
</code></pre> 

PhotoUploadActivity contains AsyncTask that creates parcel from assets and chutes.
<pre><code>
 public void createParcel(GCLocalAssetCollection assets, GCChuteCollection chutes) {
	GCParcel.create(getApplicationContext(), assets, chutes,
		new GCCreateParcelsUploadsListParser(), new GCParcelCreateCallback())
		.executeAsync();
    }
</code></pre> 

The AsyncTask returns response data equivalent to GCLocalAssetCollection needed for image upload.
Another AsyncTask is executed which successfully uploads the image in the chute.
<pre><code>
            GCAssets.upload(getApplicationContext(),
			new GCUploadProgressListenerImplementation(), new GCStringResponse(),
			new GCHttpUploadCallback(), responseData).executeAsync();
</code></pre> 


## Request execution and callback

 Every request can be either:
-synchronous (it executes in the same thread as the <code>execute()</code> method was called
-asynchronous (it executes in the Background and it is started by calling <code>executeAsync()</code>);

 Every request can accept a custom response parser or use the default parser for each request type and a suitable callback which will return an object or a collection depending on the response type
 The callback has 4 possible outcomes

	<pre>
	// returns the parsed response according to the parsers return type.
	
	<code>public void onSuccess(T responseData); </code>
    
	// it returns an object that will contain the request parameters, the URL, the headers and the Request Type (GET, POST, PUT, DELETE)
	// this happens if there was a timeout and the request didn't reach the server (usually due to connectivity issues)
    
	<code>public void onHttpException(GCHttpRequestParameters params, Throwable exception); </code>
	
	// this happens when the server didn't process the result correctly, it returns a HTTP Status code and an error message
    
	<code>public void onHttpError(int responseCode, String statusMessage);</code>
	
	// This happens when the parser didn't successfully parse the response string, usually this requires adjustments on the client side and it is not recoverable by retries
	
	<code>public void onParserException(int responseCode, Throwable exception);</code>
	</pre>
				    	    