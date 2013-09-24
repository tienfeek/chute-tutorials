package com.chute.android.imageuploader.activity;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.chute.android.onestepuploadtestproject.R;
import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;

import darko.imagedownloader.ImageLoader;

public class UploadApp extends Application {

	private ImageLoader mImageLoader;
	private static final String TOKEN = "2459ce0016bdbacd8c3eaa23333b183f0e9d6aa8322ad63fa06eed3d40162844";
	private static final String CLIENT_ID = "4f3c39ff38ecef0c89000003";
	private static final String CLIENT_SECRET = "c9a8cb57c52f49384ab6117c4f6483a1a5c5a14c4a50d4cef276a9a13286efc9";
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		mImageLoader = createImageLoader(this);
		Chute.init(this, new AuthConstants(CLIENT_ID, CLIENT_SECRET));
	}

	private static ImageLoader createImageLoader(Context context) {
		ImageLoader imageLoader = new ImageLoader(context, R.drawable.ic_launcher);
		imageLoader.setDefaultBitmapSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, context
				.getResources().getDisplayMetrics()));
		return imageLoader;
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
