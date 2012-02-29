package com.chute.android.joinchutetutorial.app;

import org.apache.http.HttpStatus;

import com.chute.android.joinchutetutorial.R;
import com.chute.android.joinchutetutorial.intent.SearchChutesActivityIntentWrapper;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.membership.GCMembership;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.parsers.GCMembershipJoinParser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class JoinChuteTutorialActivity extends Activity {
	private static final String SHORTCUT = "volvqd";
	private static final String DOMAIN = "http://chutedemos.heroku.com";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Test token, see GCAuthentication activity on how to authenticate
		final GCAccountStore account = GCAccountStore
				.getInstance(getApplicationContext());
		account.setPassword("46b7c778447e18ee5865a83f4202f42a2f85283c47ef24541366509235d8eccf");

		final Button chuteShortcut = (Button) findViewById(R.id.btnChuteShortcut);
		chuteShortcut.setOnClickListener(new ChuteShortcutClickListener());

		final Button chuteDomain = (Button) findViewById(R.id.btnChuteDomain);
		chuteDomain.setOnClickListener(new ChuteDomainClickListener());

	}

	private final class ChuteShortcutClickListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			GCMembership.join(getApplicationContext(), SHORTCUT, null,
					new GCMembershipJoinParser(), new GCJoinChuteCallback())
					.executeAsync();
		}

	}

	private final class GCJoinChuteCallback implements GCHttpCallback<String> {

		@Override
		public void onSuccess(final String responseData) {
			Toast.makeText(getApplicationContext(), responseData,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpException(final GCHttpRequestParameters params,
				final Throwable exception) {
			Toast.makeText(getApplicationContext(),
					R.string.http_exception,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpError(final int responseCode, final String statusMessage) {
			switch (responseCode) {
			case HttpStatus.SC_NOT_FOUND:
				Toast.makeText(
						getApplicationContext(),
						R.string.chute_not_found,
						Toast.LENGTH_SHORT).show();

				break;
			case HttpStatus.SC_UNAUTHORIZED:
				Toast.makeText(
						getApplicationContext(),
						R.string.chute_unauthorized,
						Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(getApplicationContext(),
						R.string.server_issue, Toast.LENGTH_SHORT).show();
				break;
			}
		}

		@Override
		public void onParserException(final int responseCode, final Throwable exception) {
			Toast.makeText(getApplicationContext(), R.string.parser_exception,
					Toast.LENGTH_SHORT).show();
		}

	}

	private final class ChuteDomainClickListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final SearchChutesActivityIntentWrapper wrapper = new SearchChutesActivityIntentWrapper(
					JoinChuteTutorialActivity.this);
			wrapper.setShortcut(SHORTCUT);
			wrapper.setDomain(DOMAIN);
			wrapper.startActivity(JoinChuteTutorialActivity.this);
		}

	}

}