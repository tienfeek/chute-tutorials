Introduction
====

Gallery Listing Tutorial is a tutorial project that shows how to use the Gallery Listing component. It contains Chute SDK library as well as Gallery Listing library.
This tutorial demonstrates a list of chutes. It takes the current user ID and displays GCChuteCollection. This tutorial also shows how to update a chute, delete a chute and invite someone to a chute.

![image1](https://github.com/chute/chute-tutorials/raw/master/Android/Gallery%20Listing%20Tutorial/screenshots/1.png) ![image2](https://github.com/chute/chute-tutorials/raw/master/Android/Gallery%20Listing%20Tutorial/screenshots/2.png)![image3](https://github.com/chute/chute-tutorials/raw/master/Android/Gallery%20Listing%20Tutorial/screenshots/3.png) ![image4](https://github.com/chute/chute-tutorials/raw/master/Android/Gallery%20Listing%20Tutorial/screenshots/4.png) ![image5](https://github.com/chute/chute-tutorials/raw/master/Android/Gallery%20Listing%20Tutorial/screenshots/5.png)![image6](https://github.com/chute/chute-tutorials/raw/master/Android/Gallery%20Listing%20Tutorial/screenshots/6.png)

Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* Add the Gallery Listing component to your project by either copying all the resources and source code or by adding it as an Android Library project.
  Gallery Listing component can be found and downloaded at [https://github.com/chute/chute-android-components/tree/master/Gallery%20Listing](https://github.com/chute/chute-android-components/tree/master/Gallery%20Listing).

* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
         <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".app.GalleryListingTutorialApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Black.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:name=".app.GalleryListingTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".app.ChuteListActivity"
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:screenOrientation="portrait" />
         <activity 
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:screenOrientation="portrait"
            android:name=".app.ChuteInviteActivity" />
        <activity 
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:screenOrientation="portrait"
            android:name=".app.ChuteDescriptionActivity" />
        <activity
            android:name="com.chute.android.gcchutelisting.app.GalleryListingActivity"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Light.NoTitleBar" />
        <activity
            android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
            android:screenOrientation="portrait"
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

GalleryListingTutorialApp can also be neglected by registering GalleryListingApp into the manifest instead of GalleryListingTutoiralApp if the developer doesn't have the need for extending the Application class.
   
##GalleryListingTutorialActivity.java
This class is an Activity class that contains a "Show Gallery List" button. When the button is clicked, ChuteListActivity is started.

<pre><code> 
Button showList = (Button) findViewById(R.id.btnShowList);
		showList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ChuteListActivity.class);
				startActivity(intent);
			}
		});
</code></pre> 

##ChuteListActivity.java
This Activity class extends GalleryListingActivity from GalleryListing component. GalleryListingActivity contains list of chutes. When list item is clicked a dialog with four buttons appears.
When "Invite" button gets clicked, ChuteInviteActivityIntentWrapper starts ChuteInviteActivity. ChuteInviteActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.

<pre><code>
invite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChuteInviteActivityIntentWrapper wrapper = new ChuteInviteActivityIntentWrapper(
						ListingChutesTutorialActivity.this);
				wrapper.setChuteId(model.getId());
				wrapper.setChuteName(model.getName());
				wrapper.startActivity(ListingChutesTutorialActivity.this);
				dialog.dismiss();
			}
		});
</code></pre>

When "Description" button gets clicked, ChuteDescriptionActivityIntentWrapper starts ChuteDescriptionActivity.
<pre><code>
Button description = (Button) dialog
				.findViewById(R.id.buttonDescription);
		description.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChuteDescriptionActivityIntentWrapper wrapper = new ChuteDescriptionActivityIntentWrapper(
						ListingChutesTutorialActivity.this);
				wrapper.setChuteModel(model);
				wrapper.startActivity(ListingChutesTutorialActivity.this);
			}
		});
</code></pre>

When "Update" button gets clicked, new name is set on the GCChuteModel and <code>GCChutes.update(Context context, GCChuteModel chuteModel, GCHttpCallback<GCChuteModel> callback)</code> callback is started. 
<pre><code>
Button update = (Button) dialog.findViewById(R.id.buttonUpdate);
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				model.setName("new");
				GCChutes.updateChute(getApplicationContext(), model,
						new ChuteUpdateCallback()).executeAsync();
				dialog.dismiss();
			}
		});
</code></pre>
<code>GCChutes.update(Context context, GCChuteModel chuteModel, GCHttpCallback<GCChuteModel> callback)</code> callback returns new updated GCChuteModel in its response data. If the callback succeeds, Toast message appears indicating that the chute has been updated.

When "Delete" button gets clicked, the selected chute ID is passed to the <code>GCChutes.delete(Context context, String id, GCHttpCallback<GCChuteCollection> callback)</code> callback. If the callback succeeds, Toast message appears indicating that the selected chute has been deleted.
<pre><code>
Button delete = (Button) dialog.findViewById(R.id.buttonDelete);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GCChutes.delete(getApplicationContext(), model.getId(),
						new GCChuteListObjectParser(),
						new ChuteDeleteCallback()).executeAsync();
				dialog.dismiss();
			}
		});
</code></pre>

##ChuteInviteActivity.java
This Activity class contains ListView and two buttons. The list is filled with user contacts. When "Ok" button is clicked, <code>GCMembership.invite(Context context, String chuteId, ArrayList<String> emails, GCHttpResponseParser<T> parser, GCHttpCallback<T> callback)</code> callback is executed and the selected contacts are invited to join the chute. 
If the callback succeeds, Toast message appears indicating the invites have been sent.
<pre><code>
private final class OkClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			GCMembership.invite(getApplicationContext(), wrapper.getChuteId(), adapter.getSelectedEmailsList(), new GCStringResponse(), new ChuteInviteCallback()).executeAsync();
			finish();
		}

	}
</code></pre>

##ChuteDescriptionActivity.java
This Activity class contains chute description that represents a GCChuteModel: ID, name, parcel ID, user ID, password, user, members, contributors, recent, creation date, updating date, assets, thumbnail URL, shortcut, permission view, permission to add members, permission to add photos, permission to add comments, permission to moderate members, permission to moderate photos and permission to moderate comments.			
  

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
				 