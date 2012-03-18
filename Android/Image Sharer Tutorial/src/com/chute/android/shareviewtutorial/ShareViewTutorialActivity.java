package com.chute.android.shareviewtutorial;

import com.chute.android.imagesharer.intent.ShareActivityIntentWrapper;
import com.chute.android.imagesharer.util.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShareViewTutorialActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button share = (Button) findViewById(R.id.btnShare);
        share.setOnClickListener(new OnShareClickListener());
    }
    
    private final class OnShareClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			ShareActivityIntentWrapper wrapper = new ShareActivityIntentWrapper(ShareViewTutorialActivity.this);
			wrapper.setChuteId(Constants.CHUTE_ID);
			wrapper.setChuteName(Constants.CHUTE_NAME);
			wrapper.setChuteShortcut(Constants.CHUTE_SHORTCUT);
			wrapper.startActivity(ShareViewTutorialActivity.this);
		}
    	
    }
}