package com.chute.android.useravatar.app;

import com.chute.android.useravatar.R;
import com.chute.android.useravatar.intent.DisplayAvatarActivityIntentWrapper;
import com.chute.sdk.model.GCUserModel;
import com.darko.imagedownloader.ImageLoader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class DisplayAvatarActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = DisplayAvatarActivity.class
			.getSimpleName();
	private DisplayAvatarActivityIntentWrapper wrapper;
	private ImageLoader loader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_avatar_activity);

		wrapper = new DisplayAvatarActivityIntentWrapper(getIntent());
		String imagePath = wrapper.getImagePath();
		Bitmap croppedImage = wrapper.getBitmap();
		
		loader = ImageLoader.get(getApplicationContext());

		ImageView image = (ImageView) findViewById(R.id.imageView);
//		loader.displayImage(imagePath, image);
		image.setImageBitmap(croppedImage);
		
		GCUserModel user = new GCUserModel();
		user.setAvatarURL(imagePath);
	}

}
