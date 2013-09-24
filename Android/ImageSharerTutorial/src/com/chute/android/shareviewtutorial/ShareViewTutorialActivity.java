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
package com.chute.android.shareviewtutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.android.imagesharer.intent.ShareActivityIntentWrapper;
import com.chute.android.imagesharer.util.Constants;
import com.chute.sdk.v2.api.asset.GCAssets;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class ShareViewTutorialActivity extends Activity {

  @SuppressWarnings("unused")
  private static final String TAG = ShareViewTutorialActivity.class
      .getSimpleName();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button share = (Button) findViewById(R.id.buttonShare);
    share.setOnClickListener(new OnShareClickListener());
  }

  private final class OnShareClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      AlbumModel album = new AlbumModel();
      album.setId(Constants.ALBUM_ID);
      GCAssets.list(getApplicationContext(), album,
          new AlbumAssetsCallback()).executeAsync();
    }

  }

  private final class AlbumAssetsCallback implements
      HttpCallback<ListResponseModel<AssetModel>> {

    @Override
    public void onSuccess(ListResponseModel<AssetModel> responseData) {
      if (responseData.getData().size() > 0) {
        ShareActivityIntentWrapper wrapper = new ShareActivityIntentWrapper(
            ShareViewTutorialActivity.this);
        wrapper.setAssetShareUrl(responseData.getData().get(0).getUrl());
        wrapper.setAlbumName(Constants.ALBUM_NAME);
        wrapper.startActivity(ShareViewTutorialActivity.this);
      } else {
        Toast.makeText(
            getApplicationContext(),
            getApplicationContext().getResources().getString(
                R.string.no_photos_in_this_album),
            Toast.LENGTH_SHORT).show();
      }

    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      ALog.d("Http Error: " + responseCode.getStatusCode() + " "
          + responseCode.getStatusMessage());
      Toast.makeText(getApplicationContext(), R.string.http_error,
          Toast.LENGTH_SHORT).show();

    }

  }
}