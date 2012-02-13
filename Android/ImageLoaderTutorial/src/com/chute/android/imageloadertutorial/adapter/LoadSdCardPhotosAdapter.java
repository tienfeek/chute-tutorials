package com.chute.android.imageloadertutorial.adapter;

import java.io.File;

import com.chute.android.imageloadertutorial.R;
import com.darko.imagedownloader.ImageLoader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

public class LoadSdCardPhotosAdapter extends CursorAdapter {

	public static final String TAG = LoadSdCardPhotosAdapter.class
			.getSimpleName();

	private static LayoutInflater inflater = null;
	public ImageLoader loader;
	private final int dataIndex;
	private boolean shouldLoadImages = true;
	private final DisplayMetrics displayMetrics;

	public LoadSdCardPhotosAdapter(Context context, Cursor c) {
		super(context, c);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loader = ImageLoader.get(context);
		dataIndex = c.getColumnIndex(MediaStore.Images.Media.DATA);
		displayMetrics = context.getResources().getDisplayMetrics();
	}

	public static class ViewHolder {
		public ImageView image;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		String path = cursor.getString(dataIndex);
		holder.image.setTag(path);
		if (shouldLoadImages) {
			loader.displayImage(Uri.fromFile(new File(path)).toString(),
					holder.image);
		} else {
			loader.displayImage(null, holder.image);
		}
		holder.image.setLayoutParams(new RelativeLayout.LayoutParams(
				displayMetrics.widthPixels / 3 - 2,
				displayMetrics.widthPixels / 3 - 2));
		holder.image.setScaleType(ScaleType.CENTER_CROP);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		ViewHolder holder;
		View vi = inflater.inflate(R.layout.grid_adapter_item, null);
		holder = new ViewHolder();
		holder.image = (ImageView) vi.findViewById(R.id.imageViewThumb);
		vi.setTag(holder);
		return vi;
	}
}
