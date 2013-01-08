package com.chute.android.albumtutorial.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.ui.fragment.AlbumFragment;

public class CreateUpdateAlbumActivity extends FragmentActivity {

	public static final String TAG = CreateUpdateAlbumActivity.class.getSimpleName();
	private AlbumFragment albumFragment;
	private Button buttonUpdate;
	private Button buttonCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_update_album_activity);

		albumFragment = (AlbumFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment);

		buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
		buttonCreate = (Button) findViewById(R.id.buttonCreate);

		buttonUpdate.setOnClickListener(new ButtonUpdateClickListener());
		buttonCreate.setOnClickListener(new ButtonCreateClickListener());

	}

	private final class ButtonUpdateClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			albumFragment.updateAlbum();
		}

	}

	private final class ButtonCreateClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			albumFragment.updateAlbum();
		}

	}

}
