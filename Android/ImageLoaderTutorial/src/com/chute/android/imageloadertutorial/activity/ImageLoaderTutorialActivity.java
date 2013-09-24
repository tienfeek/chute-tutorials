package com.chute.android.imageloadertutorial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.chute.android.imageloadertutorial.R;
import com.chute.android.imageloadertutorial.intent.LoadImageActivityIntentWrapper;

public class ImageLoaderTutorialActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_loader_tutorial);

    findViewById(R.id.buttonSdcard)
        .setOnClickListener(new OnButtonSdcardClicked());

    findViewById(R.id.buttonUrl)
        .setOnClickListener(new OnButtonUrlClicked());

    findViewById(R.id.buttonChutePhotoUrl)
        .setOnClickListener(new OnButtonChutePhotoUrlClicked());
  }

  private final class OnButtonSdcardClicked implements OnClickListener {

    @Override
    public void onClick(View v) {
      LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
          ImageLoaderTutorialActivity.this);
      wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_SDCARD);
      wrapper.startActivity(ImageLoaderTutorialActivity.this);
    }

  }

  private final class OnButtonUrlClicked implements OnClickListener {

    @Override
    public void onClick(View v) {
      LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
          ImageLoaderTutorialActivity.this);
      wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_URL);
      wrapper.startActivity(ImageLoaderTutorialActivity.this);
    }

  }

  private final class OnButtonChutePhotoUrlClicked implements OnClickListener {

    @Override
    public void onClick(View v) {
      LoadImageActivityIntentWrapper wrapper = new LoadImageActivityIntentWrapper(
          ImageLoaderTutorialActivity.this);
      wrapper.setFilterType(LoadImageActivityIntentWrapper.TYPE_CHUTE_URL);
      wrapper.startActivity(ImageLoaderTutorialActivity.this);
    }

  }
}