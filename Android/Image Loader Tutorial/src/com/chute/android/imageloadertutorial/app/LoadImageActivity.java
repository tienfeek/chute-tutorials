package com.chute.android.imageloadertutorial.app;

import java.io.File;

import com.chute.android.imageloadertutorial.R;
import com.chute.android.imageloadertutorial.dao.MediaDAO;
import com.chute.android.imageloadertutorial.intent.LoadImageActivityIntentWrapper;
import com.darko.imagedownloader.ImageLoader;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

public class LoadImageActivity extends Activity {

	public static final String TAG = LoadImageActivity.class.getSimpleName();

	private ImageView imageView;
	private ImageLoader loader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_display);

		loader = ImageLoader.getLoader(LoadImageActivity.this);

		imageView = (ImageView) findViewById(R.id.image);
		LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
				getIntent());

		switch (wrapper.getFilterType()) {
		case LoadImageActivityIntentWrapper.TYPE_SDCARD:
			Cursor cursor = MediaDAO.getMediaPhotos(LoadImageActivity.this);
			if (cursor != null && cursor.moveToFirst()) {
				String path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				loader.displayImage(Uri.fromFile(new File(path)).toString(),
						imageView);
			}
			break;
		case LoadImageActivityIntentWrapper.TYPE_URL:
			loader.displayImage(
					"http://www.sun-protection-and-you.com/images/sun-stroke.jpg",
					imageView);
			break;
		case LoadImageActivityIntentWrapper.TYPE_CHUTE_URL:
			loader.displayImage("http://sharedroll.com/volvqd", imageView);
			break;
		default:
			break;
		}

	}
}
