package com.chute.android.imageloadertutorial.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.chute.android.imageloadertutorial.R;
import com.chute.android.imageloadertutorial.adapter.LoadSdCardPhotosAdapter;
import com.chute.android.imageloadertutorial.dao.MediaDAO;

public class LoadSdCardPhotosActivity extends Activity {

	public static final String TAG = LoadSdCardPhotosActivity.class
			.getSimpleName();

	private GridView grid;
	private LoadSdCardPhotosAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_layout);

		grid = (GridView) findViewById(R.id.photoGrid);
		new LoadCursorTask().execute();
	}

	private class LoadCursorTask extends AsyncTask<Void, Void, Cursor> {

		@Override
		protected Cursor doInBackground(final Void... arg0) {
			return MediaDAO.getMediaPhotos(getApplicationContext());
		}

		@Override
		protected void onPostExecute(final Cursor result) {
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			if (adapter == null) {
				adapter = new LoadSdCardPhotosAdapter(
						LoadSdCardPhotosActivity.this, result);
				grid.setAdapter(adapter);
				grid.setOnItemClickListener(new OnGridItemClickListener());
			} else {
				adapter.changeCursor(result);
			}
		}
	}

	private final class OnGridItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long id) {
			finish();
		}
	}

}
