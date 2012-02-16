package com.chute.android.imagegridtutorial.app;

import com.chute.android.imagegrid.intent.ImageGridIntentWrapper;
import com.chute.android.imagegridtutorial.R;
import com.chute.sdk.model.GCAccountStore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImageGridTutorialActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GCAccountStore account = GCAccountStore.getInstance(getApplicationContext());
    	account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

    	Button showAssetsButton = (Button) findViewById(R.id.btnShowAssets);
    	showAssetsButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
    	ImageGridIntentWrapper wrapper = new ImageGridIntentWrapper(this);
    	wrapper.setChuteID("684");
    	wrapper.startActivity(this);
        }

    
}