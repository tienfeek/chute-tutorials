package com.chute.android.localimagepickertutorial.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.chute.android.localimagepickertutorial.R;
import com.chute.android.multiimagepicker.intent.ChoosePhotosActivityIntentWrapper;
import com.chute.sdk.v2.model.AssetModel;

public class MultiImagePickerTutorialActivity extends Activity implements
		OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button selectPhotos = (Button) findViewById(R.id.btnSelectPhotos);
		selectPhotos.setOnClickListener(this);
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