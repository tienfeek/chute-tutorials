package com.chute.android.albumtutorial.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.chute.android.albumtutorial.R;
import com.chute.android.gcchutelisting.app.GalleryListingActivity;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AlbumListActivity extends GalleryListingActivity {

	public static final String TAG = AlbumListActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		listView.setOnItemClickListener(new AlbumsClickListener());

	}

	private final class AlbumsClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			AlbumModel model = (AlbumModel) listView.getAdapter().getItem(
					position);
			showDialog(model);
		}

	}

	public void showDialog(final AlbumModel model) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.album_list_dialog);
		Button delete = (Button) dialog.findViewById(R.id.buttonDelete);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GCAlbums.delete(getApplicationContext(), model,
						new AlbumDeleteCallback()).executeAsync();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private final class AlbumDeleteCallback implements
			HttpCallback<ResponseModel<AlbumModel>> {

		@Override
		public void onSuccess(ResponseModel<AlbumModel> responseData) {
			Toast.makeText(getApplicationContext(), "Album deleted!",
					Toast.LENGTH_SHORT).show();
			// TODO refresh album list

		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			Toast.makeText(getApplicationContext(), "http error",
					Toast.LENGTH_SHORT).show();

		}

	}

}
