package com.chute.android.chutelistingtutorial.app;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.chute.android.chutelistingtutorial.R;
import com.chute.android.chutelistingtutorial.intent.ChuteDescriptionActivityIntentWrapper;
import com.chute.android.chutelistingtutorial.intent.ChuteInviteActivityIntentWrapper;
import com.chute.android.gcchutelisting.app.GalleryListingActivity;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.parsers.GCChuteListObjectParser;

public class GalleryListingTutorialActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GCAccountStore account = GCAccountStore
				.getInstance(getApplicationContext());
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

		Button showList = (Button) findViewById(R.id.btnShowList);
		showList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ChuteListActivity.class);
				startActivity(intent);
			}
		});
	}

}