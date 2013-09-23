package com.chute.android.cloudgallerytutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CloudGalleryTutorialActivity extends Activity {

  @SuppressWarnings("unused")
  private static final String TAG = CloudGalleryTutorialActivity.class
      .getSimpleName();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.buttonStartCloudGallery).setOnClickListener(
        new OnStartClickListener());
  }

  private final class OnStartClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      Intent intent = new Intent(getApplicationContext(),
          GalleryActivity.class);
      startActivity(intent);
    }

  }
}