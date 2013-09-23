package com.chute.android.commentstutorial;

import com.chute.android.comments.intent.PhotoCommentsActivityIntentWrapper;
import com.chute.android.comments.intent.MainActivityIntentWrapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PhotoCommentsTutorialActivity extends Activity implements
    OnClickListener {

  @SuppressWarnings("unused")
  private static final String TAG = PhotoCommentsTutorialActivity.class
      .getSimpleName();
  private static final int ACTIVITY_FOR_RESULT_KEY = 113;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button startComments = (Button) findViewById(R.id.btnStartComments);
    startComments.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    PhotoCommentsActivityIntentWrapper wrapper = new PhotoCommentsActivityIntentWrapper(
        this);
    wrapper.setAlbumId("2399241"); // Replace with album id
    wrapper.setAssetId("338549979"); // Replace with asset id
    wrapper.setAlbumName("Reykjavik"); // Name of the album
    wrapper.startActivityForResult(this, ACTIVITY_FOR_RESULT_KEY);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode != ACTIVITY_FOR_RESULT_KEY
        || resultCode != RESULT_OK) {
      return;
    }
    MainActivityIntentWrapper wrapper = new MainActivityIntentWrapper(data);
    if (wrapper.getExtraComments() > 0) {
      Toast.makeText(
          getApplicationContext(),
          wrapper.getExtraComments()
              + getApplicationContext().getResources().getString(
                  R.string.comments_added),
          Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(
          getApplicationContext(),
          getApplicationContext().getResources().getString(
              R.string.no_comments_added), Toast.LENGTH_SHORT)
          .show();
    }
  }

}