package com.chute.android.commentstutorial;

import com.chute.android.comments.util.Constants;
import com.chute.android.comments.util.intent.PhotoCommentsActivityIntentWrapper;
import com.chute.android.comments.util.intent.MainActivityIntentWrapper;
import com.chute.sdk.v2.model.AccountStore;
import com.dg.libs.rest.authentication.TokenAuthenticationProvider;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TokenAuthenticationProvider.init(this);

		AccountStore account = AccountStore
				.getInstance(getApplicationContext());
		// Test token, see AuthenticationActivity on how to authenticate
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

		Button startComments = (Button) findViewById(R.id.btnStartComments);
		startComments.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		PhotoCommentsActivityIntentWrapper wrapper = new PhotoCommentsActivityIntentWrapper(
				this);
		wrapper.setAlbumId("1946"); // Replace with album id
		wrapper.setAssetId("5868"); // Replace with asset id for which to enter
		// and
		// view comments
		wrapper.setAlbumName("Album Name"); // Name of the album
		wrapper.startActivityForResult(this, Constants.ACTIVITY_FOR_RESULT_KEY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode != Constants.ACTIVITY_FOR_RESULT_KEY
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