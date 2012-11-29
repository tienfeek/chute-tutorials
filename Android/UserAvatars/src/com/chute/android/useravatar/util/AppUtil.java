package com.chute.android.useravatar.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

public class AppUtil {

	public static final String TAG = AppUtil.class.getSimpleName();

	private AppUtil() {
	}

	public static File getFilefromBitmap(Context context, Bitmap bitmap) {
		File file = null;
		try {
			String path = Environment.getExternalStorageDirectory().toString();
			OutputStream fOut = null;
			file = new File(path, "1" + ".jpg");
			fOut = new FileOutputStream(file);

			bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
			fOut.flush();
			fOut.close();
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), file.getName(), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;

	}
}
