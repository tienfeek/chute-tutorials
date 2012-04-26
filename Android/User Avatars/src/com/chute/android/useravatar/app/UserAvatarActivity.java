package com.chute.android.useravatar.app;

import java.io.File;
import java.util.ArrayList;

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
import android.widget.Toast;

import com.chute.android.photopickerplus.util.intent.PhotoActivityIntentWrapper;
import com.chute.android.photopickerplus.util.intent.PhotoPickerPlusIntentWrapper;
import com.chute.android.useravatar.R;
import com.chute.android.useravatar.imagemanipulation.CropImage;
import com.chute.android.useravatar.model.UploadModel;
import com.chute.android.useravatar.parsers.UploadParser;
import com.chute.android.useravatar.util.AppUtil;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.asset.GCAssets;
import com.chute.sdk.api.asset.GCUploadProgressListener;
import com.chute.sdk.api.parcel.GCParcel;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.model.GCLocalAssetModel;
import com.chute.sdk.model.response.GCParcelCreateResponse;
import com.chute.sdk.parsers.GCCreateParcelsUploadsListParser;
import com.chute.sdk.utils.GCUtils;
import com.darko.imagedownloader.FileCache;
import com.darko.imagedownloader.ImageLoader;

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
			final PhotoPickerPlusIntentWrapper wrapper = new PhotoPickerPlusIntentWrapper(UserAvatarActivity.this);
			wrapper.setMultiPicker(false);
			wrapper.setChuteId(CHUTE_TEST_ID);
			wrapper.startActivityForResult(UserAvatarActivity.this, REQUEST_CODE_PP);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == REQUEST_CODE_PP) {
			final PhotoActivityIntentWrapper wrapper = new PhotoActivityIntentWrapper(data);
			final int width = 200;
			final int height = 200;
			
			Uri uri = Uri.parse(wrapper.getMediaCollection().get(0).getUrl());
			if (uri.getScheme().contentEquals("http")) {
			new DownloadBitmapTask(uri.toString()).execute();
			} else {
//			tempFileForCroppedImage = new FileCache(getApplicationContext())
//					.getFile(data.getData().getPath());
			tempFileForCroppedImage = new FileCache(getApplicationContext()).getFile(uri.getPath());
			tempFileForCroppedImage.deleteOnExit();
			Log.d(TAG, tempFileForCroppedImage.getPath());
			Intent intent = new Intent(this, CropImage.class);
//			intent.setData(data.getData());
			
			intent.setData(Uri.fromFile(new File(uri.getPath())));
			intent.putExtra("outputX", width);
			intent.putExtra("outputY", height);
			intent.putExtra("aspectX", width);
			intent.putExtra("aspectY", height);
			intent.putExtra("scale", true);
			intent.putExtra("noFaceDetection", true);

			intent.putExtra("output", Uri.fromFile(tempFileForCroppedImage));
			startActivityForResult(intent, REQUEST_CROP_IMAGE);
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
			Log.d("debug", "stringot vo doinbackground" + string);
			return ImageLoader.getLoader(getApplicationContext()).downloadBitmap(string);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			File file = AppUtil.getFilefromBitmap(getApplicationContext(), result);
			tempFileForCroppedImage = new FileCache(getApplicationContext()).getFile(file.getAbsolutePath());
			tempFileForCroppedImage.deleteOnExit();
			Log.d(TAG, tempFileForCroppedImage.getPath());
			Intent intent = new Intent(getApplicationContext(), CropImage.class);
			
			intent.setData(Uri.fromFile(new File(file.getAbsolutePath())));
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 200);
			intent.putExtra("aspectY", 200);
			intent.putExtra("scale", true);
			intent.putExtra("noFaceDetection", true);

			intent.putExtra("output", Uri.fromFile(tempFileForCroppedImage));
			startActivityForResult(intent, REQUEST_CROP_IMAGE);
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
		GCParcel.create(getApplicationContext(), assets, chutes,
				new GCCreateParcelsUploadsListParser(),
				new GCParcelCreateCallback()).executeAsync();
	}

	private final class GCParcelCreateCallback implements
			GCHttpCallback<GCParcelCreateResponse> {

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			Log.d(TAG, "Parcel Create http Error ", exception);
			Toast.makeText(getApplicationContext(), R.string.http_exception,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			Toast.makeText(getApplicationContext(), R.string.http_error,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			Toast.makeText(getApplicationContext(), R.string.parsing_exception,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onSuccess(GCParcelCreateResponse responseData) {
			try {
				GCAssets.upload(getApplicationContext(),
						new GCUploadProgressListenerImplementation(),
						new UploadParser(), new GCHttpUploadCallback(),
						responseData.getLocalAssetCollection()).executeAsync();
			} catch (Exception e) {
				Log.w(TAG, "", e);
			}
			Log.d(TAG, responseData.toString());
		}
	}

	private final class GCUploadProgressListenerImplementation implements
			GCUploadProgressListener {

		@Override
		public void onUploadStarted(String assetId, final String filepath,
				final Bitmap thumbnail) {
			Log.d(TAG, "Upload started");
			pb.setMax(100);
			pb.setProgress(0);
		}

		@Override
		public void onUploadFinished(String assetId, String filepath) {
			Log.d(TAG, "Upload finished");
		}

		@Override
		public void onProgress(long total, long uploaded) {
			int percent = (int) ((uploaded * 100) / total);
			Log.d(TAG, "Current progress Text" + percent + "%");
			pb.setProgress(percent);
		}
	}

	private class GCHttpUploadCallback implements
			GCHttpCallback<ArrayList<UploadModel>> {
		@Override
		public void onSuccess(ArrayList<UploadModel> responseData) {
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
			Log.d(TAG, "Upload callback Create http exception ", exception);
			Toast.makeText(getApplicationContext(), R.string.http_exception,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			Log.d(TAG, "Upload callback http Error " + statusMessage + " Code "
					+ responseCode);
			Toast.makeText(getApplicationContext(), R.string.http_error,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			Log.d(TAG,
					"Upload callback Parser Exception  Code " + responseCode,
					exception);
			Toast.makeText(getApplicationContext(), R.string.parsing_exception,
					Toast.LENGTH_SHORT).show();

		}
	}
	
}