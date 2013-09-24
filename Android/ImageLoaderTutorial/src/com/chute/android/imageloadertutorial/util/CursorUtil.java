package com.chute.android.imageloadertutorial.util;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class CursorUtil {

  private static final String TAG = CursorUtil.class.getSimpleName();

  public static Cursor getAllMediaPhotos(final Context context) {
    final String[] projection = new String[] { MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATA };
    final Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    return context.getContentResolver().query(images, projection, null, null,
        MediaStore.Images.Media.DATE_ADDED + " DESC");
  }

  public static Uri getLastPhotoFromAllPhotos(final Context context) {
    Cursor allMediaPhotos = getAllMediaPhotos(context);
    Uri uri = getFirstItemUri(allMediaPhotos);
    safelyCloseCursor(allMediaPhotos);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  private static Uri getFirstItemUri(Cursor allMediaPhotos) {
    if (allMediaPhotos != null && allMediaPhotos.moveToFirst()) {
      return Uri.fromFile(new File(allMediaPhotos.getString(allMediaPhotos
          .getColumnIndex(MediaStore.Images.Media.DATA))));
    }
    return null;
  }

  public static void safelyCloseCursor(final Cursor c) {
    try {
      if (c != null) {
        c.close();
      }
    } catch (Exception e) {
      Log.d(TAG, "", e);
    }
  }
}
