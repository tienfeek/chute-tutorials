package com.chute.android.imageloadertutorial.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chute.android.imageloadertutorial.activity.LoadImageActivity;

public class LoadImageActivityIntentWrapper {

  public static final String TAG = LoadImageActivityIntentWrapper.class
      .getSimpleName();

  private static final String EXTRA_KEY_PHOTO = "extraKeyPhoto";

  public static final int TYPE_SDCARD = 0;
  public static final int TYPE_URL = 1;
  public static final int TYPE_CHUTE_URL = 2;

  private final Intent intent;

  public LoadImageActivityIntentWrapper(Intent intent) {
    super();
    this.intent = intent;
  }

  public LoadImageActivityIntentWrapper(Context packageContext, Class<?> cls) {
    super();
    intent = new Intent(packageContext, cls);
  }

  public LoadImageActivityIntentWrapper(Context packageContext) {
    super();
    intent = new Intent(packageContext, LoadImageActivity.class);
  }

  public Intent getIntent() {
    return intent;
  }

  public void setFilterType(int type) {
    intent.putExtra(EXTRA_KEY_PHOTO, type);
  }

  public int getFilterType() {
    return intent.getExtras().getInt(EXTRA_KEY_PHOTO);
  }

  public void startActivity(Activity context) {
    context.startActivity(intent);
  }
}
