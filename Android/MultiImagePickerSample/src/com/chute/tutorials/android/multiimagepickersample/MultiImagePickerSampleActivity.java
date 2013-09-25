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
package com.chute.tutorials.android.multiimagepickersample;

import java.util.ArrayList;
import java.util.List;

import com.chute.components.android.multiimagepicker.intent.ChoosePhotosActivityIntentWrapper;
import com.chute.sdk.v2.model.AssetModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MultiImagePickerSampleActivity extends Activity implements
    OnClickListener {

  public static final String TAG = MultiImagePickerSampleActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button selectPhotos = (Button) findViewById(R.id.btnSelectPhotos);
    selectPhotos.setOnClickListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public void onClick(View v) {
    ChoosePhotosActivityIntentWrapper wrapper = new ChoosePhotosActivityIntentWrapper(
        this);
    wrapper.startActivityForResult(this,
        ChoosePhotosActivityIntentWrapper.ACTIVITY_FOR_RESULT_KEY);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ChoosePhotosActivityIntentWrapper.ACTIVITY_FOR_RESULT_KEY) {
      if (resultCode == RESULT_OK) {
        ChoosePhotosActivityIntentWrapper wrapper = new ChoosePhotosActivityIntentWrapper(
            data);
        List<AssetModel> assetList = makeAssetList(wrapper
            .getAssetPathList());
        int photosSelected = assetList.size();
        Toast.makeText(getApplicationContext(),
            photosSelected + " photos selected!",
            Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(getApplicationContext(), "No photos selected",
            Toast.LENGTH_SHORT).show();
      }
    }
  }

  public List<AssetModel> makeAssetList(ArrayList<String> gridSelectedFilePath) {
    List<AssetModel> list = new ArrayList<AssetModel>();
    AssetModel asset = new AssetModel();
    for (String filePath : gridSelectedFilePath) {
      asset.setUrl(filePath);
      list.add(asset);
    }
    return list;
  }

}