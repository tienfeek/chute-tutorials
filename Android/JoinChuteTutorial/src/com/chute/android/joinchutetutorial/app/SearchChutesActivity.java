package com.chute.android.joinchutetutorial.app;

import org.apache.http.HttpStatus;

import com.chute.android.joinchutetutorial.R;
import com.chute.android.joinchutetutorial.adapter.ChuteListAdapter;
import com.chute.android.joinchutetutorial.intent.SearchChutesActivityIntentWrapper;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.api.membership.GCMembership;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCHttpRequestParameters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchChutesActivity extends Activity {

	private ListView chutes;
	private ChuteListAdapter adapter;
	private SearchChutesActivityIntentWrapper wrapper;

	public static final String TAG = SearchChutesActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chute_list);

		chutes = (ListView) findViewById(R.id.listView);
		wrapper = new SearchChutesActivityIntentWrapper(getIntent());
		GCChutes.search(getApplicationContext(), wrapper.getDomain(),
				new SearchChutesCallback()).executeAsync();

	}

	private final class SearchChutesCallback implements
			GCHttpCallback<GCChuteCollection> {

		@Override
		public void onSuccess(GCChuteCollection responseData) {
			adapter = new ChuteListAdapter(SearchChutesActivity.this,
					responseData);
			chutes.setAdapter(adapter);
			chutes.setOnItemClickListener(new ChuteClickListener());
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			Toast.makeText(getApplicationContext(), R.string.http_exception,
					Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			Toast.makeText(getApplicationContext(), R.string.server_issue,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			Toast.makeText(getApplicationContext(), R.string.parser_exception,
					Toast.LENGTH_SHORT).show();

		}

	}

	private final class ChuteClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			GCMembership.join(getApplicationContext(),
					adapter.getItem(position).getShortcut(),
					adapter.getItem(position).getPassword(),
					new ChuteJoinCallback()).executeAsync();
		}

	}

	private final class ChuteJoinCallback implements GCHttpCallback<String> {

		@Override
		public void onSuccess(String responseData) {
			Toast.makeText(getApplicationContext(), responseData,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			Toast.makeText(getApplicationContext(), R.string.http_exception,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			switch (responseCode) {
			case HttpStatus.SC_NOT_FOUND:
				Toast.makeText(getApplicationContext(),
						R.string.chute_not_found, Toast.LENGTH_SHORT).show();

				break;
			case HttpStatus.SC_UNAUTHORIZED:
				Toast.makeText(getApplicationContext(),
						R.string.chute_unauthorized, Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(getApplicationContext(), R.string.server_issue,
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			Toast.makeText(getApplicationContext(), R.string.parser_exception,
					Toast.LENGTH_SHORT).show();
		}

	}
}
