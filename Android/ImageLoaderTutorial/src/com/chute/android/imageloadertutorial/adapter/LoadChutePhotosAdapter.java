package com.chute.android.imageloadertutorial.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chute.android.imageloadertutorial.R;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCChuteModel;
import com.darko.imagedownloader.ImageLoader;

public class LoadChutePhotosAdapter extends BaseAdapter {

	public static final String TAG = LoadChutePhotosAdapter.class
			.getSimpleName();

	private final GCChuteCollection collection;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private final Activity context;

	public LoadChutePhotosAdapter(final Activity context,
			final GCChuteCollection collection) {
		super();
		this.context = context;
		this.collection = collection;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = ImageLoader.get(context);
	}

	@Override
	public int getCount() {
		return collection.size();
	}

	@Override
	public GCChuteModel getItem(int position) {
		return collection.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public ImageView image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.grid_adapter_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) vi.findViewById(R.id.imageViewThumb);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		imageLoader.displayImage(getItem(position).getRecentThumbnailURL(),
				holder.image);
		return vi;
	}
}
