package com.chute.android.useravatar.app;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.chute.android.useravatar.R;
import com.chute.android.useravatar.adapter.SingleImagePickerAdapter;
import com.chute.android.useravatar.dao.MediaDAO;

public class SingleImagePickerActivity extends Activity {

    @SuppressWarnings("unused")
    private static final String TAG = SingleImagePickerActivity.class.getSimpleName();
    private GridView grid;
    private SingleImagePickerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.single_image_picker_activity);
	grid = (GridView) findViewById(R.id.gridView);
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
		adapter = new SingleImagePickerAdapter(SingleImagePickerActivity.this, result);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnGridItemClickListener());
	    } else {
		adapter.changeCursor(result);
	    }
	}
    }

    private class OnGridItemClickListener implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	    String imagePath = adapter.getItem(position);
	    Intent extras = new Intent();
	    extras.setData(Uri.fromFile(new File(imagePath)));
	    setResult(Activity.RESULT_OK, extras);
	    finish();
	}
    }
}
