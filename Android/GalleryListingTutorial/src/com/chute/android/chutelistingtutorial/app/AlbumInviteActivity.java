package com.chute.android.chutelistingtutorial.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.chute.android.chutelistingtutorial.R;
import com.chute.android.chutelistingtutorial.adapter.AlbumInviteAdapter;
import com.chute.android.chutelistingtutorial.dao.ContactsDAO;
import com.chute.android.chutelistingtutorial.intent.AlbumInviteActivityIntentWrapper;

public class AlbumInviteActivity extends Activity {

	public static final String TAG = AlbumInviteActivity.class.getSimpleName();

	private ListView list;
	private AlbumInviteAdapter adapter;
	private AlbumInviteActivityIntentWrapper wrapper;
	private Button ok;
	private Button cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_invite_activity);

		wrapper = new AlbumInviteActivityIntentWrapper(getIntent());

		list = (ListView) findViewById(R.id.listViewAlbumInvite);
		list.setEmptyView(findViewById(R.id.progressBarEmpty));
		ok = (Button) findViewById(R.id.buttonOk);
		ok.setOnClickListener(new OkClickListener());
		cancel = (Button) findViewById(R.id.buttonCancel);
		cancel.setOnClickListener(new CancelClickListener());

		new InviteTask().execute();
	}

	private class InviteTask extends AsyncTask<Void, Void, Cursor> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Cursor doInBackground(Void... params) {
			return ContactsDAO.getContactsWithEmails(getApplicationContext());
		}

		@Override
		protected void onPostExecute(Cursor result) {
			try {
				adapter = new AlbumInviteAdapter(AlbumInviteActivity.this,
						result);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new ListItemClickListener());
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				Toast.makeText(
						getApplicationContext(),
						getApplicationContext().getResources().getString(
								R.string.error_fetching_contact_list),
						Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);

		}
	}

	private final class ListItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Cursor c = adapter.getCursor();
			c.moveToPosition(position);
			if (adapter.tick.containsKey(position)) {
				adapter.tick.remove(position);
			} else {
				adapter.tick.put(position,
						c.getString(c.getColumnIndex(Email.DATA)));
			}
			adapter.notifyDataSetChanged();

		}

	}

	private final class CancelClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			finish();
		}

	}

	private final class OkClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// GCMembership.invite(getApplicationContext(),
			// wrapper.getChuteId(),
			// adapter.getSelectedEmailsList(), new GCStringResponse(),
			// new ChuteInviteCallback()).executeAsync();
			finish();
		}

	}

	// private final class ChuteInviteCallback implements GCHttpCallback<String>
	// {
	//
	// @Override
	// public void onSuccess(String responseData) {
	// Toast.makeText(
	// getApplicationContext(),
	// getApplicationContext().getResources().getString(
	// R.string.invite), Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// public void onHttpException(GCHttpRequestParameters params,
	// Throwable exception) {
	// Toast.makeText(getApplicationContext(), R.string.http_exception,
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// public void onHttpError(int responseCode, String statusMessage) {
	// Toast.makeText(getApplicationContext(), R.string.http_error,
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// public void onParserException(int responseCode, Throwable exception) {
	// Toast.makeText(getApplicationContext(), R.string.parsing_exception,
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// }
}
