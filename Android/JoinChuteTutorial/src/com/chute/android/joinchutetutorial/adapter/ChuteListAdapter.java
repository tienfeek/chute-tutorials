package com.chute.android.joinchutetutorial.adapter;

import com.chute.android.joinchutetutorial.R;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCChuteModel;
import com.darko.imagedownloader.ImageLoader;
import com.darko.imagedownloader.ImageLoaderListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChuteListAdapter extends BaseAdapter implements
		ImageLoaderListener {

	public static final String TAG = ChuteListAdapter.class.getSimpleName();
	private final GCChuteCollection collection;
	private final Activity context;
	private final ImageLoader loader;
	private static LayoutInflater inflater;

	public ChuteListAdapter(final Activity context,
			final GCChuteCollection collection) {
		super();
		this.collection = collection;
		this.context = context;
		loader = ImageLoader.getLoader(context);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return collection.size();
	}

	@Override
	public GCChuteModel getItem(final int position) {
		return collection.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	private static class ViewHolder {
		ImageView image;
		TextView name;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.chute_list_adapter_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) vi.findViewById(R.id.chuteThumb);
			holder.name = (TextView) vi.findViewById(R.id.chuteName);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		loader.displayImage(getItem(position).getRecentThumbnailURL(),
				holder.image, this);
		holder.name.setText(getItem(position).getName());
		return vi;
	}

	@Override
	public void onImageLoadingComplete(String url, Bitmap bitmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onImageLoadingError() {
		// TODO Auto-generated method stub

	}
}
