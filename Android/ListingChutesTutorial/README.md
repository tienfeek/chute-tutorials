Introduction
====

ListingChutesTutorial is a tutorial project that shows how to update a chute, delete a chute and invite someone to a chute. It contains Chute SDK library as well as GalleryListing library. 

![image1](https://github.com/chute/chute-tutorials/raw/master/Android/ListingChutesTutorial/screenshots/1.png) ![image2](https://github.com/chute/chute-tutorials/raw/master/Android/ListingChutesTutorial/screenshots/2.png) ![image3](https://github.com/chute/chute-tutorials/raw/master/Android/ListingChutesTutorial/screenshots/3.png) ![image4](https://github.com/chute/chute-tutorials/raw/master/Android/ListingChutesTutorial/screenshots/4.png) ![image5](https://github.com/chute/chute-tutorials/raw/master/Android/ListingChutesTutorial/screenshots/5.png)


Setup
====

* Go through [ProjectSetup.md](https://github.com/chute/photo-picker-plus/blob/master/Android/PhotoPickerPlusTutorial/ProjectSetup.md) for a complete guide on how to setup the chute SDK.
  
* Add the GalleryListing component to your project by either copying all the resources and source code or by adding it as an Android Library project.

* The next thing you need to do is register the activities, services and the application class into the AndroidManifest.xml file:

    ```
        <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@android:style/Theme.Light.NoTitleBar"
        android:name=".app.ListingChutesApp" >
        <service android:name="com.chute.sdk.api.GCHttpService" />

        <activity
            android:label="@string/app_name"
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:name=".app.ListingChutesTutorialActivity" >
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
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:name=".app.ChuteInviteActivity" />
        <activity 
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:name=".app.ChuteDescriptionActivity" />
        </application>
    ```


Usage
====

##ListingChutesApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
ListingChutesApp can extend GalleryListingApp like shown in this tutorial:

<pre><code>
public class ListingChutesApp extends GalleryListingApp {

}
</code></pre>

This way the developer can use his own methods and code inside the Application class. 

If the developer decides to extend the Application class instead of GalleryListingApp he must copy the all the code below:

<pre><code>
public class ListingChutesApp extends Application {
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

ListingChutesApp can also be neglected by registering GalleryListingApp into the manifest instead of ListingChutesApp if the developer doesn't have the need for extending the Application class.

##ListingChutesTutorialActivity.java 
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

When "Update" button gets clicked, new name is set on the GCChuteModel and <code>GCChutes.update()</code> callback is started. 
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
<code>GCChutes.update()</code> callback returns new updated GCChuteModel in its response data. If the callback succeeds, Toast message appears indicating that the chute has been updated.

When "Delete" button gets clicked, the selected chute ID is passed to the <code>GCChutes.delete()</code> callback. If the callback succeeds, Toast message appears indicating that the selected chute has been deleted.
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
This Activity class contains ListView and two buttons. The list is filled with user contacts. When "Ok" button is clicked, <code>GCMembership.invite()</code> callback is executed and the selected contacts are invited to join the chute. 
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
 				
