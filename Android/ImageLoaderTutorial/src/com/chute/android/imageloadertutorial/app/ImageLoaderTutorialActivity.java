package com.chute.android.imageloadertutorial.app;

import com.chute.android.imageloadertutorial.R;
import com.chute.android.imageloadertutorial.R.id;
import com.chute.android.imageloadertutorial.R.layout;
import com.chute.sdk.model.GCAccountStore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImageLoaderTutorialActivity extends Activity {
	
	private Button btnSdcard;
	private Button btnUrl;
	private Button btnChutePhotoUrl; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GCAccountStore account = GCAccountStore.getInstance(getApplicationContext());
    	account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");
        
        btnSdcard = (Button) findViewById(R.id.btnSdcard);
        btnSdcard.setOnClickListener(new OnButtonSdcardClicked());
        
        btnUrl = (Button) findViewById(R.id.btnUrl);
        btnUrl.setOnClickListener(new OnButtonUrlClicked());
        
        btnChutePhotoUrl = (Button) findViewById(R.id.btnChutePhotoUrl);
        btnChutePhotoUrl.setOnClickListener(new OnButtonChutePhotoUrlClicked());
    }
    
    private final class OnButtonSdcardClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(), LoadSdCardPhotosActivity.class);
			startActivity(intent);
		}
    	
    }
    
    private final class OnButtonUrlClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    private final class OnButtonChutePhotoUrlClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(), LoadChutePhotosActivity.class);
			startActivity(intent);
		}
    	
    }
}