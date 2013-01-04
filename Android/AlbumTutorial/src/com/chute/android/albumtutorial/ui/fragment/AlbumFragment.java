package com.chute.android.albumtutorial.ui.fragment;

import com.chute.android.albumtutorial.R;
import com.chute.android.albumtutorial.ui.activity.CreateAlbumActivity;
import com.chute.sdk.v2.model.AlbumModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AlbumFragment extends Fragment {

	public static final String TAG = AlbumFragment.class.getSimpleName();
	private TextView id;
	private TextView links;
	private TextView createdAt;
	private TextView updatedAt;
	private TextView shortcut;
	private TextView name;
	private TextView description;
	private TextView user;
	private TextView moderateComments;
	private TextView moderateMedia;
	private TextView counters;
	private AlbumModel album = new AlbumModel();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.album_fragment, null);
		id = (TextView) fragmentView.findViewById(R.id.txtId);
		links = (TextView) fragmentView.findViewById(R.id.txtLinks);
		createdAt = (TextView) fragmentView.findViewById(R.id.txtCreatedAt);
		updatedAt = (TextView) fragmentView.findViewById(R.id.txtUpdatedAt);
		shortcut = (TextView) fragmentView.findViewById(R.id.txtShortcut);
		name = (TextView) fragmentView.findViewById(R.id.txtName);
		description = (TextView) fragmentView.findViewById(R.id.txtDescription);
		user = (TextView) fragmentView.findViewById(R.id.txtUser);
		moderateComments = (TextView) fragmentView
				.findViewById(R.id.txtModerateComments);
		moderateMedia = (TextView) fragmentView
				.findViewById(R.id.txtModerateMedia);
		counters = (TextView) fragmentView.findViewById(R.id.txtCounters);
		CreateAlbumActivity activity = (CreateAlbumActivity) getActivity();
		album = activity.getAlbumModel();
		return fragmentView;
	}

	public void fillAlbumModel(AlbumModel album) {
		id.setText(getResources().getString(R.string.txt_id) + " "
				+ album.getId());
		links.setText(getResources().getString(R.string.txt_links) + " "
				+ album.getLinks());
		createdAt.setText(getResources().getString(R.string.txt_created_at)
				+ " " + album.getCreatedAt());
		updatedAt.setText(getResources().getString(R.string.txt_updated_at)
				+ " " + album.getUpdatedAt());
		shortcut.setText(getResources().getString(R.string.txt_shortcut) + " "
				+ album.getShortcut());
		name.setText(getResources().getString(R.string.txt_name) + " "
				+ album.getName());
		description.setText(getResources().getString(R.string.txt_description)
				+ " " + album.getDescription());
		user.setText(getResources().getString(R.string.txt_user) + " "
				+ album.getUser().toString());
		moderateComments
				.setText(getResources().getString(
						R.string.txt_moderate_comments)
						+ " "
						+ (album.isModerateComments() == true ? "true"
								: "false"));
		moderateMedia.setText(getResources().getString(
				R.string.txt_moderate_media)
				+ " " + (album.isModerateMedia() == true ? "true" : "false"));
		if (album.getCounters() != null) {
			counters.setText(getResources().getString(R.string.txt_counters)
					+ " " + album.getCounters().toString());
		}
	}
}
