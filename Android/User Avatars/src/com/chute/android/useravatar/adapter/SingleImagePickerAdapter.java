package com.chute.android.useravatar.adapter;

import java.io.File;

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
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.chute.android.useravatar.R;
import com.darko.imagedownloader.ImageLoader;

public class SingleImagePickerAdapter extends CursorAdapter {

    @SuppressWarnings("unused")
    private static final String TAG = SingleImagePickerAdapter.class.getSimpleName();
    private static LayoutInflater inflater = null;
    public ImageLoader loader;
    private final DisplayMetrics displayMetrics;
    private final int dataIndex;

    public SingleImagePickerAdapter(Context context, Cursor c) {
	super(context, c);
	inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	loader = ImageLoader.getLoader(context);
	displayMetrics = context.getResources().getDisplayMetrics();
	dataIndex = c.getColumnIndex(MediaStore.Images.Media.DATA);
    }

    public static class ViewHolder {
	public ImageView image;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
	ViewHolder holder = (ViewHolder) view.getTag();
	String path = cursor.getString(dataIndex);
	loader.displayImage(Uri.fromFile(new File(path)).toString(), holder.image);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
	ViewHolder holder;

	View vi = inflater.inflate(R.layout.single_image_picker_adapter, null);
	holder = new ViewHolder();
	holder.image = (ImageView) vi.findViewById(R.id.imageViewThumb);
	holder.image.setLayoutParams(new LinearLayout.LayoutParams(
		displayMetrics.widthPixels / 3 - 2, displayMetrics.widthPixels / 3 - 2));
	holder.image.setScaleType(ScaleType.FIT_XY);
	vi.setTag(holder);
	return vi;
    }

    @Override
    public String getItem(int position) {
	final Cursor cursor = getCursor();
	cursor.moveToPosition(position);
	return cursor.getString(dataIndex);
    }
}