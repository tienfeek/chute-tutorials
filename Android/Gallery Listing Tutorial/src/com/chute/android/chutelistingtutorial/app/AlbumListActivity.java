package com.chute.android.chutelistingtutorial.app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.chute.android.chutelistingtutorial.R;
import com.chute.android.chutelistingtutorial.intent.AlbumDescriptionActivityIntentWrapper;
import com.chute.android.gcchutelisting.app.GalleryListingActivity;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AlbumListActivity extends GalleryListingActivity {

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
		// Button invite = (Button) dialog.findViewById(R.id.buttonInvite);

		// invite.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// AlbumInviteActivityIntentWrapper wrapper = new
		// AlbumInviteActivityIntentWrapper(
		// AlbumListActivity.this);
		// wrapper.setAlbumId(model.getId());
		// wrapper.setAlbumName(model.getName());
		// wrapper.startActivity(AlbumListActivity.this);
		// dialog.dismiss();
		// }
		// });
		Button description = (Button) dialog
				.findViewById(R.id.buttonDescription);
		description.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlbumDescriptionActivityIntentWrapper wrapper = new AlbumDescriptionActivityIntentWrapper(
						AlbumListActivity.this);
				wrapper.setAlbumModel(model);
				wrapper.startActivity(AlbumListActivity.this);
			}
		});
		Button delete = (Button) dialog.findViewById(R.id.buttonDelete);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GCAlbums.delete(getApplicationContext(), model,
						new ChuteDeleteCallback()).executeAsync();
				dialog.dismiss();
			}
		});
		Button update = (Button) dialog.findViewById(R.id.buttonUpdate);
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				model.setName("new");
				GCAlbums.edit(getApplicationContext(), model,
						new AlbumUpdateCallback()).executeAsync();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private final class ChuteDeleteCallback implements
			HttpCallback<ResponseModel<AlbumModel>> {

		@Override
		public void onSuccess(ResponseModel<AlbumModel> responseData) {
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.album_deleted), Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			Toast.makeText(getApplicationContext(), R.string.http_error,
					Toast.LENGTH_SHORT).show();

		}

	}

	private final class AlbumUpdateCallback implements
			HttpCallback<ResponseModel<AlbumModel>> {

		@Override
		public void onSuccess(ResponseModel<AlbumModel> responseData) {
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.album_updated), Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			Toast.makeText(getApplicationContext(), R.string.http_exception,
					Toast.LENGTH_SHORT).show();

		}

	}
}