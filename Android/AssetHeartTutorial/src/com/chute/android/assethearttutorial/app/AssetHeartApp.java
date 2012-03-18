package com.chute.android.assethearttutorial.app;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.chute.android.assethearttutorial.R;
import com.chute.android.assethearttutorial.R.drawable;
import com.darko.imagedownloader.ImageLoader;

public class AssetHeartApp extends Application {

	public static final String TAG = AssetHeartApp.class.getSimpleName();

	private static ImageLoader createImageLoader(Context context) {
		ImageLoader imageLoader = new ImageLoader(context, R.drawable.placeholder_image_small);
		imageLoader.setDefaultImageSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 75, context.getResources()
						.getDisplayMetrics()));
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
