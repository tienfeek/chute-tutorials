package com.chute.android.tutorials.basicfunctionalities;

import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.requests.ListResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import android.app.Activity;
import android.os.Bundle;

public class BasicFunctionalitiesDemoActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Now since the authentication has passed successfully we can use the
		// sdk methods

		/**
		 * Every SDK method that is made available is accessed through one of
		 * the static classes: GCAlbums GCAssets GCComments GCVotes etc... Each
		 * of these classes are located inside specific packages inside the API
		 * package in the Chute SDK project.
		 * 
		 * Each method returns an object implementing the HTTP request interface
		 * which has two methods: executeAsync() allows the developer to execute
		 * the request asynchronously; execute() allows the developer to execute
		 * the request in the current thread that the call was made.
		 * 
		 * The callback has 2 methods. One of those methods will be called
		 * according to the specific outcome of the request.
		 * 
		 * 
		 */

		// EXAMPLE: fetch a list of all the albums
		GCAlbums.list(getApplicationContext(), new AlbumsAllCallback())
				.executeAsync();
	}

	private final class AlbumsAllCallback implements
			HttpCallback<ListResponseModel<AlbumModel>> {

		@Override
		public void onSuccess(ListResponseModel<AlbumModel> responseData) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			// TODO Auto-generated method stub

		}

	}
}