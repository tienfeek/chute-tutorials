package com.chute.android.listingchutestutorial.app;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.chute.android.gcchutelisting.app.GalleryListingActivity;
import com.chute.android.listingchutestutorial.R;
import com.chute.android.listingchutestutorial.intent.ChuteDescriptionActivityIntentWrapper;
import com.chute.android.listingchutestutorial.intent.ChuteInviteActivityIntentWrapper;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.parsers.GCChuteListObjectParser;

public class ListingChutesTutorialActivity extends GalleryListingActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GCAccountStore account = GCAccountStore
				.getInstance(getApplicationContext());
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

		listView.setOnItemClickListener(new ChutesClickListener());

	}

	private final class ChutesClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			GCChuteModel model = (GCChuteModel) listView.getAdapter().getItem(
					position);
			showDialog(model);
		}

	}

	public void showDialog(final GCChuteModel model) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.chute_list_dialog);
		Button invite = (Button) dialog.findViewById(R.id.buttonInvite);

		invite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChuteInviteActivityIntentWrapper wrapper = new ChuteInviteActivityIntentWrapper(
						ListingChutesTutorialActivity.this);
				wrapper.setChuteId(model.getId());
				wrapper.setChuteName(model.getName());
				wrapper.startActivity(ListingChutesTutorialActivity.this);
				dialog.dismiss();
			}
		});
		Button description = (Button) dialog
				.findViewById(R.id.buttonDescription);
		description.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChuteDescriptionActivityIntentWrapper wrapper = new ChuteDescriptionActivityIntentWrapper(
						ListingChutesTutorialActivity.this);
				wrapper.setChuteModel(model);
				wrapper.startActivity(ListingChutesTutorialActivity.this);
			}
		});
		Button delete = (Button) dialog.findViewById(R.id.buttonDelete);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GCChutes.delete(getApplicationContext(), model.getId(),
						new GCChuteListObjectParser(),
						new ChuteDeleteCallback()).executeAsync();
				dialog.dismiss();
			}
		});
		Button update = (Button) dialog.findViewById(R.id.buttonUpdate);
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				model.setName("new");
				GCChutes.updateChute(getApplicationContext(), model,
						new ChuteUpdateCallback()).executeAsync();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private final class ChuteDeleteCallback implements
			GCHttpCallback<GCChuteCollection> {

		@Override
		public void onSuccess(GCChuteCollection responseData) {
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.chute_deleted), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			// TODO Auto-generated method stub

		}

	}

	private final class ChuteUpdateCallback implements
			GCHttpCallback<GCChuteModel> {

		@Override
		public void onSuccess(GCChuteModel responseData) {
			Toast.makeText(
					getApplicationContext(),
					getApplicationContext().getResources().getString(
							R.string.chute_updated), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			// TODO Auto-generated method stub

		}

	}
}