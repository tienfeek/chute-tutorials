package com.chute.android.chutelistingtutorial.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.chute.android.chutelistingtutorial.R;

import darko.imagedownloader.ImageLoader;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumInviteAdapter extends CursorAdapter {

	public static final String TAG = AlbumInviteAdapter.class.getSimpleName();

	private static LayoutInflater inflater = null;
	private ImageLoader loader;
	private Activity context;
	public HashMap<Integer, String> tick;

	public AlbumInviteAdapter(Activity context, Cursor c) {
		super(context, c);
		this.context = context;
		loader = ImageLoader.getLoader(context);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tick = new HashMap<Integer, String>();
	}

	public class ViewHolder {
		public ImageView image;
		public ImageView tick;
		public TextView title;
		public TextView description;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder;
		holder = (ViewHolder) view.getTag();
		try {
			Uri person = ContentUris
					.withAppendedId(
							ContactsContract.Contacts.CONTENT_URI,
							Long.parseLong(cursor.getString(cursor
									.getColumnIndex(ContactsContract.CommonDataKinds.Photo.CONTACT_ID))));
			person = Uri.withAppendedPath(person,
					ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
			loader.displayImage(person.toString(), holder.image);
		} catch (Exception e) {
			Log.d(TAG, "", e);
		}
		holder.title
				.setText(cursor.getString(cursor
						.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME)));
		holder.description.setText(cursor.getString(cursor
				.getColumnIndex(Email.DATA)));

		if (tick.containsKey(cursor.getPosition())) {
			holder.image.setBackgroundColor(context.getResources().getColor(
					R.color.orange_backgound));
			holder.tick.setVisibility(View.VISIBLE);
		} else {
			holder.image.setBackgroundColor(context.getResources().getColor(
					R.color.transparent));
			holder.tick.setVisibility(View.GONE);
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		ViewHolder holder;
		View vi = inflater.inflate(R.layout.album_invite_adapter, null);
		holder = new ViewHolder();
		holder.image = (ImageView) vi.findViewById(R.id.imageViewThumb);
		holder.tick = (ImageView) vi.findViewById(R.id.imageViewTick);
		holder.title = (TextView) vi.findViewById(R.id.textViewTitle);
		holder.description = (TextView) vi
				.findViewById(R.id.textViewDescription);
		vi.setTag(holder);
		return vi;
	}

	public ArrayList<String> getSelectedEmailsList() throws RuntimeException,
			NullPointerException {
		if (tick.size() == 0) {
			throw new RuntimeException("No email items selected");
		}
		ArrayList<String> list = new ArrayList<String>();
		Iterator<Integer> iterator = tick.keySet().iterator();
		Cursor cursor = getCursor();
		int emailIndex = cursor.getColumnIndex(Email.DATA);
		while (iterator.hasNext()) {
			cursor.moveToPosition(iterator.next());
			list.add(cursor.getString(emailIndex));
		}
		return list;
	}

}
