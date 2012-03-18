package com.chute.android.shareviewtutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chute.android.imagesharer.intent.ShareActivityIntentWrapper;
import com.chute.android.imagesharer.util.Constants;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCAssetCollection;
import com.chute.sdk.model.GCHttpRequestParameters;

public class ShareViewTutorialActivity extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = ShareViewTutorialActivity.class
			.getSimpleName();

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
			GCChutes.Resources.assets(getApplicationContext(),
					Constants.CHUTE_ID, new GCChuteModelAssetsCallback())
					.executeAsync();
		}

	}

	private final class GCChuteModelAssetsCallback implements
			GCHttpCallback<GCAssetCollection> {

		@Override
		public void onSuccess(GCAssetCollection responseData) {
			ShareActivityIntentWrapper wrapper = new ShareActivityIntentWrapper(
					ShareViewTutorialActivity.this);
			wrapper.setAssetShareUrl(responseData.get(0).getShareUrl());
			wrapper.setChuteId(Constants.CHUTE_ID);
			wrapper.setChuteName(Constants.CHUTE_NAME);
			wrapper.setChuteShortcut(Constants.CHUTE_SHORTCUT);
			wrapper.startActivity(ShareViewTutorialActivity.this);
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