package com.chute.android.listingchutestutorial.app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.chute.android.gcchutelisting.app.GalleryListingActivity;
import com.chute.android.listingchutestutorial.R;
import com.chute.android.listingchutestutorial.intent.ChuteInviteActivityIntentWrapper;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCChuteModel;

public class ListingChutesTutorialActivity extends GalleryListingActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		GCAccountStore account = GCAccountStore.getInstance(getApplicationContext());
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

		listView.setOnItemClickListener(new ChutesClickListener());
		
	}
	
	private final class ChutesClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			 GCChuteModel model = (GCChuteModel) listView.getAdapter().getItem(arg2);
			 showDialog(model);
		}

	}
	
	public void showDialog(final GCChuteModel model) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.chute_list_dialog);
		Button invite = (Button) dialog.findViewById(R.id.buttonInvite);
		invite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ChuteInviteActivityIntentWrapper wrapper = new ChuteInviteActivityIntentWrapper(ListingChutesTutorialActivity.this);
				wrapper.setChuteId(model.getId());
				wrapper.setChuteName(model.getName());
				wrapper.startActivity(ListingChutesTutorialActivity.this);
				dialog.dismiss();
			}
		});
		Button description = (Button) dialog.findViewById(R.id.buttonDescription);
		Button update = (Button) dialog.findViewById(R.id.buttonUpdate);
		Button delete= (Button) dialog.findViewById(R.id.buttonDelete);
		dialog.show();
	}
 }