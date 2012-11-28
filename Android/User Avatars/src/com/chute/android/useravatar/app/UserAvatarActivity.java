package com.chute.android.useravatar.app;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chute.android.photopickerplus.util.intent.PhotoActivityIntentWrapper;
import com.chute.android.photopickerplus.util.intent.PhotoPickerPlusIntentWrapper;
import com.chute.android.useravatar.R;
import com.chute.android.useravatar.util.AppUtil;
import com.chute.android.useravatar.util.intent.CropImageIntentWrapper;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.asset.GCAssets;
import com.chute.sdk.api.asset.GCUploadProgressListener;
import com.chute.sdk.collections.GCAssetCollection;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCAssetModel;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.model.GCLocalAssetModel;
import com.chute.sdk.utils.GCUtils;
import com.chute.sdk.utils.Logger;
import darko.imagedownloader.FileCache;
import darko.imagedownloader.ImageLoader;

public class UserAvatarActivity extends Activity {
	private static final String CHUTE_TEST_ID = "684";

	private static final String TAG = UserAvatarActivity.class.getSimpleName();

	private static final int REQUEST_CROP_IMAGE = 42;
	public static int REQUEST_CODE_PP = 1232;

	private ImageView thumb;

	private TextView path;

	private ProgressBar pb;

	private File tempFileForCroppedImage;

	private GCChuteModel chuteModel;

	private ImageLoader loader;

	private int width = 200;
	private int height = 200;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button choosePhoto = (Button) findViewById(R.id.btnChoosePhoto);
		choosePhoto.setOnClickListener(new ChoosePhotoClickListener());

		// Test token, see GCAuthentication activity on how to authenticate
		GCAccountStore
				.getInstance(getApplicationContext())
				.setPassword(
						"d3149b3ce0f1bc15a6330df8a8b1c431d2d40a853763022cbf1bb2817a30dbfa");
		loader = ImageLoader.getLoader(this);

		thumb = (ImageView) findViewById(R.id.imageViewThumb);
		path = (TextView) findViewById(R.id.textViewPath);
		pb = (ProgressBar) findViewById(R.id.progressBarUpload);

		chuteModel = new GCChuteModel();
		chuteModel.setId(CHUTE_TEST_ID); // Test ID for a public chute called
		// Upload Test
	}

	private class ChoosePhotoClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final PhotoPickerPlusIntentWrapper wrapper = new PhotoPickerPlusIntentWrapper(
					UserAvatarActivity.this);
			wrapper.setMultiPicker(false);
			wrapper.setChuteId(CHUTE_TEST_ID);
			wrapper.startActivityForResult(UserAvatarActivity.this,
					REQUEST_CODE_PP);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == REQUEST_CODE_PP) {
			final PhotoActivityIntentWrapper wrapper = new PhotoActivityIntentWrapper(
					data);

			Uri uri = Uri.parse(wrapper.getMediaCollection().get(0).getUrl());
			if (uri.getScheme().contentEquals("http")) {
				new DownloadBitmapTask(uri.toString()).execute();
			} else {
				tempFileForCroppedImage = new FileCache(getApplicationContext())
						.getFile(uri.getPath());
				tempFileForCroppedImage.deleteOnExit();
				Log.d(TAG, tempFileForCroppedImage.getPath());
				CropImageIntentWrapper cropWrapper = new CropImageIntentWrapper(
						UserAvatarActivity.this);
				cropWrapper.setOutputX(width);
				cropWrapper.setOutputY(height);
				cropWrapper.setAspectX(width);
				cropWrapper.setAspectY(height);
				cropWrapper.setScale(true);
				cropWrapper.setNoFaceDetection(true);
				cropWrapper.setUri(Uri.fromFile(new File(uri.getPath())));
				cropWrapper.setOutput(Uri.fromFile(tempFileForCroppedImage));
				cropWrapper.startActivityForResult(UserAvatarActivity.this,
						REQUEST_CROP_IMAGE);

				return;
			}
		}
		if (requestCode == REQUEST_CROP_IMAGE) {
			String imagePath = data.getStringExtra("imagePath");
			Bitmap croppedImage = data.getParcelableExtra("image");
			thumb.setImageBitmap(croppedImage);
			path.setText(data.getData().toString());
			uploadPhoto(data.getData().getPath());
		}
	}

	private class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

		private String string;

		public DownloadBitmapTask(String string) {
			this.string = string;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			return ImageLoader.getLoader(getApplicationContext())
					.downloadBitmap(string);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			File file = AppUtil.getFilefromBitmap(getApplicationContext(),
					result);
			tempFileForCroppedImage = new FileCache(getApplicationContext())
					.getFile(file.getAbsolutePath());
			tempFileForCroppedImage.deleteOnExit();
			Log.d(TAG, tempFileForCroppedImage.getPath());

			CropImageIntentWrapper cropWrapper = new CropImageIntentWrapper(
					UserAvatarActivity.this);
			cropWrapper.setOutputX(width);
			cropWrapper.setOutputY(height);
			cropWrapper.setAspectX(width);
			cropWrapper.setAspectY(height);
			cropWrapper.setScale(true);
			cropWrapper.setNoFaceDetection(true);
			cropWrapper.setUri(Uri.fromFile(new File(file.getAbsolutePath())));
			cropWrapper.setOutput(Uri.fromFile(tempFileForCroppedImage));
			cropWrapper.startActivityForResult(UserAvatarActivity.this,
					REQUEST_CROP_IMAGE);
			return;

		}

	}

	private void uploadPhoto(String imagePath) {
		GCLocalAssetModel asset = new GCLocalAssetModel();
		File file = new File(imagePath);
		asset.setFile(file);
		GCLocalAssetCollection assetCollection = new GCLocalAssetCollection();
		assetCollection.add(asset);

		GCChuteCollection chuteCollection = new GCChuteCollection();
		chuteCollection.add(chuteModel);

		createParcel(assetCollection, chuteCollection);
	}

	private void createParcel(GCLocalAssetCollection assets,
			GCChuteCollection chutes) {
		GCAssets.upload(getApplicationContext(),
				new GCUploadProgressListenerImplementation(),
				new AssetUploadCallback(), assets, chutes).executeAsync();
	}

	private final class AssetUploadCallback implements
			GCHttpCallback<GCAssetCollection> {

		@Override
		public void onSuccess(GCAssetCollection responseData) {
			Log.d(TAG, responseData.toString());
			final String url = responseData.get(0).getUrl();
			UserAvatarActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					loader.displayImage(
							GCUtils.getCustomSizePhotoURL(url, 75, 75), thumb);
				}
			});
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			Logger.e(TAG, "Upload callback create Http Exception ", exception);
		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			Logger.e(TAG, "Upload callback Http Error " + statusMessage
					+ " Code " + responseCode);
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			Logger.e(TAG, "Upload callback Parser Exception  Code "
					+ responseCode, exception);
		}

	}

	private final class GCUploadProgressListenerImplementation implements
			GCUploadProgressListener {

		@Override
		public void onUploadStarted(GCAssetModel asset, Bitmap thumbnail) {
			Log.d(TAG, "Upload started");
			pb.setMax(100);
			pb.setProgress(0);
		}

		@Override
		public void onProgress(long total, long uploaded) {
			int percent = (int) ((uploaded * 100) / total);
			Log.d(TAG, "Current progress Text" + percent + "%");
			pb.setProgress(percent);
		}

		@Override
		public void onUploadFinished(GCAssetModel assetModel) {
			Log.d(TAG, "Upload finished");
		}
	}

}