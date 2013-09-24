/**
 * The MIT License (MIT)

Copyright (c) 2013 Chute

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.chute.android.tutorials.basicfunctionalities;

import android.app.Activity;
import android.os.Bundle;

import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class BasicFunctionalitiesDemoActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    // Now since the authentication has passed successfully we can use the
    // sdk methods

    /**
     * Every SDK method that is made available is accessed through one of the
     * static classes: GCAlbums GCAssets GCComments GCVotes etc... Each of these
     * classes are located inside specific packages inside the API package in
     * the Chute SDK project.
     * 
     * Each method returns an object implementing the HTTP request interface
     * which has two methods: executeAsync() allows the developer to execute the
     * request asynchronously; execute() allows the developer to execute the
     * request in the current thread that the call was made.
     * 
     * The callback has 2 methods. One of those methods will be called according
     * to the specific outcome of the request.
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