package com.chute.android.useravatar.app;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chute.android.useravatar.R;
import com.chute.android.useravatar.imagemanipulation.CropImage;
import com.chute.android.useravatar.intent.SingleImagePickerIntentWrapper;
import com.chute.android.useravatar.model.UploadModel;
import com.chute.android.useravatar.parsers.UploadParser;
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
import com.chute.sdk.parsers.GCCreateParcelsUploadsListParser;
import com.chute.sdk.utils.GCUtils;
import com.darko.imagedownloader.FileCache;
import com.darko.imagedownloader.ImageLoader;

public class UserAvatarActivity extends Activity {
    private static final String CHUTE_TEST_ID = "684";

    private static final String TAG = UserAvatarActivity.class.getSimpleName();

    private static final int REQUEST_CROP_IMAGE = 42;

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

	GCAccountStore.getInstance(getApplicationContext()).setPassword(
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
	    SingleImagePickerIntentWrapper wrapper = new SingleImagePickerIntentWrapper(
		    getApplicationContext());
	    wrapper.startActivityForResult(UserAvatarActivity.this);
	}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode != Activity.RESULT_OK) {
	    return;
	}
	if (requestCode == SingleImagePickerIntentWrapper.ACTIVITY_REQUEST_CODE) {
	    final int width = 200;
	    final int height = 200;
	    tempFileForCroppedImage = new FileCache(getApplicationContext()).getFile(data.getData()
		    .getPath());
	    tempFileForCroppedImage.deleteOnExit();
	    Log.d(TAG, tempFileForCroppedImage.getPath());
	    Intent intent = new Intent(this, CropImage.class);
	    intent.setData(data.getData());
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
	if (requestCode == REQUEST_CROP_IMAGE) {
	    String imagePath = data.getStringExtra("imagePath");
	    Bitmap croppedImage = data.getParcelableExtra("image");
	    thumb.setImageBitmap(croppedImage);
	    path.setText(data.getData().toString());
	    uploadPhoto(data.getData().getPath());
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

    private void createParcel(GCLocalAssetCollection assets, GCChuteCollection chutes) {
	GCParcel.create(getApplicationContext(), assets, chutes,
		new GCCreateParcelsUploadsListParser(), new GCParcelCreateCallback())
		.executeAsync();
    }

    private final class GCParcelCreateCallback implements GCHttpCallback<GCLocalAssetCollection> {

	@Override
	public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
	    Log.d(TAG, "Parcel Create http Error ", exception);
	}

	@Override
	public void onHttpError(int responseCode, String statusMessage) {
	}

	@Override
	public void onParserException(int responseCode, Throwable exception) {
	}

	@Override
	public void onSuccess(GCLocalAssetCollection responseData) {
	    try {
		GCAssets.upload(getApplicationContext(),
			new GCUploadProgressListenerImplementation(), new UploadParser(),
			new GCHttpUploadCallback(), responseData).executeAsync();
	    } catch (Exception e) {
		Log.w(TAG, "", e);
	    }
	    Log.d(TAG, responseData.toString());
	}
    }

    private final class GCUploadProgressListenerImplementation implements GCUploadProgressListener {

	@Override
	public void onUploadStarted(String assetId, final String filepath, final Bitmap thumbnail) {
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

    private class GCHttpUploadCallback implements GCHttpCallback<ArrayList<UploadModel>> {
	@Override
	public void onSuccess(ArrayList<UploadModel> responseData) {
	    Log.d(TAG, responseData.toString());
	    final String url = responseData.get(0).getUrl();
	    UserAvatarActivity.this.runOnUiThread(new Runnable() {

		@Override
		public void run() {
		    loader.displayImage(GCUtils.getCustomSizePhotoURL(url, 75, 75), thumb);
		}
	    });

	}

	@Override
	public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
	    Log.d(TAG, "Upload callback Create http exception ", exception);
	}

	@Override
	public void onHttpError(int responseCode, String statusMessage) {
	    Log.d(TAG, "Upload callback http Error " + statusMessage + " Code " + responseCode);
	}

	@Override
	public void onParserException(int responseCode, Throwable exception) {
	    Log.d(TAG, "Upload callback Parser Exception  Code " + responseCode, exception);

	}
    }
}