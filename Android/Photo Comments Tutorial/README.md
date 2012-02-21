Introduction
====

Photo Comments Tutorial is a tutorial project that shows how to use the Photo Comments component. It contains Chute SDK library as well as Photo Comments library. 
This tutorial demonstrates how to add comments on an Asset. Asset represents any photo managed by Chute. 


Setup
====

* Follow the ProjectSetup tutorial that can be found and downloaded at  
  [https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup](https://github.com/chute/chute-tutorials/tree/master/Android/Project%20Setup) for a complete guide on how to setup the chute SDK.
  
* Add the Photo Comments component to your project by either copying all the resources and source code or by adding it as an Android Library project.
  Photo Comments component can be found and downloaded at [https://github.com/chute/chute-android-components/tree/master/Photo%20Comments](https://github.com/chute/chute-android-components/tree/master/Photo%20Comments).

* The next thing you need to do is register the activities and the application class into the AndroidManifest.xml file:

    ```
         <application
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".CommentsTutorialApp"
        android:screenOrientation="portrait"
        android:theme="@android:style/Theme.Black.NoTitleBar" >
        <service android:name="com.chute.sdk.api.GCHttpService" >
        </service>

        <activity
            android:label="@string/app_name"
            android:name=".CommentsTutorialActivity" >
            <intent-filter >
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:label="@string/app_name"
            android:name="com.chute.android.comments.app.PhotoCommentsActivity" >
        </activity>
        </application>
    ```

Usage
====

##PhotoCommentsTutorialApp.java 
This class is the extended Application class. It is registered inside the "application" tag in the manifest and is used for initializing the utility classes used in the component.
PhotoCommentsTutorialApp can extend PhotoCommentsApp like shown in this tutorial:

<pre><code>
public class PhotoCommentsTutorialApp extends PhotoCommentsApp {

}
</code></pre>

This way the developer can use his own methods and code inside the Application class. 

If the developer decides to extend the Application class instead of PhotoCommentsApp he must copy the all the code below:

<pre><code>
public class PhotoCommentsTutorialApp extends Application {
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

PhotoCommentsTutorialApp can also be neglected by registering PhotoCommentsApp into the manifest instead of PhotoCommentsTutoiralApp if the developer doesn't have the need for extending the Application class.
 
##PhotoCommentsTutorialActivity.java 
This class is an Activity class that contains a "Start Comments" button. When the button is clicked, PhotoCommentsActivityIntentWrapper starts PhotoCommentsActivity. PhotoCommentsActivityIntentWrapper is a wrapper class that wraps the parameters needed for the intent.

<pre><code>
 @Override
	    public void onClick(View v) {
		PhotoCommentsActivityIntentWrapper wrapper = new PhotoCommentsActivityIntentWrapper(this);
		wrapper.setChuteId("1946"); // Replace with chute id
		wrapper.setAssetId("5867"); // Replace with asset id for which to enter
					    // and
					    // view comments
		wrapper.setChuteName("Chute Name"); // Name of the chute
		wrapper.startActivityForResult(this, Constants.ACTIVITY_FOR_RESULT_KEY);
	    }
</code></pre>    

PhotoCommentsActivity contains ListView that displays the submitted comments, EditText for typing a comment and "Save" button for saving the comment.

The following callback is used for creating comments:
<pre><code>
 GCComments.add(final Context context, final String chuteId,
	    final String assetId, final String comment, final GCHttpResponseParser<T> parser,
	    final GCHttpCallback<T> callback);
		</code></pre>
The chudeId, assetId and comment are taken from the CommentsActivityIntentWrapper which has getters and setters for these parameters.		
The following callback is used to get all the comments for a specific asset:
<pre><code>
GCComments.get(final Context context, final String chuteId,
	    final String assetId, final GCHttpResponseParser<T> parser,
	    final GCHttpCallback<T> callback)
</code></pre>	
When Save button is clicked, GCComments.add callback is launched, the adapter is called and the MainActivityIntentWrapper is triggered to start the activity for result.
<pre><code>
 MainActivityIntentWrapper wrapper = new MainActivityIntentWrapper(new Intent());
		    wrapper.setExtraComments(true);
		    setResult(RESULT_OK, wrapper.getIntent());
</code></pre>

##PhotoComments Adapter
The adapter is used to fill the list of comments with GCCommentCollection. The GCComments.add callback uses GCHttpCallback<GCCommentModel> and calls the method addComment in the adapter which adds the saved comments:
<pre><code>
 public void addComment(GCCommentModel model) {
	this.collection.add(model);
	notifyDataSetChanged();
    }
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
