package com.chute.android.useravatar.app;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.chute.android.useravatar.R;
import com.chute.sdk.utils.GCPreferenceUtil;
import com.darko.imagedownloader.ImageLoader;

public class UserAvatarApp extends Application {

    @SuppressWarnings("unused")
    private static final String TAG = UserAvatarApp.class.getSimpleName();

    private static ImageLoader createImageLoader(Context context) {
	ImageLoader imageLoader = new ImageLoader(context, R.drawable.placeholder_image_small);
	imageLoader.setDefaultBitmapSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		75, context.getResources().getDisplayMetrics()));
	return imageLoader;
    }

    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
	super.onCreate();
	mImageLoader = createImageLoader(this);
	GCPreferenceUtil.init(getApplicationContext());
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
