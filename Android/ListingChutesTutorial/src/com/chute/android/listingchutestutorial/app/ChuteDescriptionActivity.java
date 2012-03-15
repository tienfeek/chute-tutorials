package com.chute.android.listingchutestutorial.app;

import com.chute.android.listingchutestutorial.R;
import com.chute.android.listingchutestutorial.intent.ChuteDescriptionActivityIntentWrapper;
import com.chute.sdk.model.GCChuteModel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ChuteDescriptionActivity extends Activity {

	public static final String TAG = ChuteDescriptionActivity.class
			.getSimpleName();
	private ChuteDescriptionActivityIntentWrapper wrapper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chute_description_activity);

		wrapper = new ChuteDescriptionActivityIntentWrapper(getIntent());

		init();
	}

	public void init() {
		GCChuteModel model = wrapper.getChuteModel();

		TextView chuteId = (TextView) findViewById(R.id.chuteId);
		chuteId.setText(getApplicationContext().getResources().getString(
				R.string.id)
				+ " " + model.getId());

		TextView name = (TextView) findViewById(R.id.name);
		name.setText(getApplicationContext().getResources().getString(
				R.string.name)
				+ " " + model.getName());

		TextView parcelId = (TextView) findViewById(R.id.parcelId);
		parcelId.setText(getApplicationContext().getResources().getString(
				R.string.parcel_id)
				+ " " + model.getRecentParcelId());

		TextView userId = (TextView) findViewById(R.id.userId);
		userId.setText(getApplicationContext().getResources().getString(
				R.string.user_id)
				+ " " + model.getRecentUserId());

		TextView password = (TextView) findViewById(R.id.password);
		password.setText(getApplicationContext().getResources().getString(
				R.string.password)
				+ " " + model.getPassword());

		TextView user = (TextView) findViewById(R.id.user);
		user.setText(getApplicationContext().getResources().getString(
				R.string.user)
				+ " " + model.getUser().toString());

		TextView membersCount = (TextView) findViewById(R.id.membersCount);
		membersCount.setText(getApplicationContext().getResources().getString(
				R.string.members)
				+ " " + model.getMembersCount());

		TextView contributorsCount = (TextView) findViewById(R.id.contributorsCount);
		contributorsCount.setText(getApplicationContext().getResources()
				.getString(R.string.contributors)
				+ " "
				+ model.getContributorsCount());

		TextView recentCount = (TextView) findViewById(R.id.recentCount);
		recentCount.setText(getApplicationContext().getResources().getString(
				R.string.recent)
				+ " " + model.getRecentCount());

		TextView createdAt = (TextView) findViewById(R.id.createdAt);
		createdAt.setText(getApplicationContext().getResources().getString(
				R.string.created_at)
				+ " " + model.getCreatedAt());

		TextView updatedAt = (TextView) findViewById(R.id.updatedAt);
		updatedAt.setText(getApplicationContext().getResources().getString(
				R.string.updated_at)
				+ " " + model.getUpdatedAt());

		TextView assetCount = (TextView) findViewById(R.id.assetsCount);
		assetCount.setText(getApplicationContext().getResources().getString(
				R.string.assets)
				+ " " + model.getAssetsCount());

		TextView thumbUrl = (TextView) findViewById(R.id.thumbUrl);
		thumbUrl.setText(getApplicationContext().getResources().getString(
				R.string.thumb_url)
				+ " " + model.getRecentThumbnailURL());

		TextView shortcut = (TextView) findViewById(R.id.shortcut);
		shortcut.setText(getApplicationContext().getResources().getString(
				R.string.shortcut)
				+ " " + model.getId());

		TextView permissionView = (TextView) findViewById(R.id.permissionView);
		permissionView.setText(getApplicationContext().getResources()
				.getString(R.string.permission_view)
				+ " "
				+ model.getPermissionView());

		TextView permissionAddMembers = (TextView) findViewById(R.id.permissionAddMembers);
		permissionAddMembers.setText(getApplicationContext().getResources()
				.getString(R.string.permission_add_members)
				+ " "
				+ model.getPermissionAddMembers());

		TextView permissionAddPhotos = (TextView) findViewById(R.id.permissionAddPhotos);
		permissionAddPhotos.setText(getApplicationContext().getResources()
				.getString(R.string.permission_add_photos)
				+ " "
				+ model.getPermissionAddPhotos());

		TextView permissionAddComments = (TextView) findViewById(R.id.permissionAddComments);
		permissionAddComments.setText(getApplicationContext().getResources()
				.getString(R.string.permission_add_comments)
				+ " "
				+ model.getPermissionAddComments());

		TextView permissionModerateMembers = (TextView) findViewById(R.id.permissionModerateMembers);
		permissionModerateMembers.setText(getApplicationContext()
				.getResources().getString(R.string.permission_moderate_members)
				+ " " + model.getPermissionModerateMembers());

		TextView permissionModeratePhotos = (TextView) findViewById(R.id.permissionModeratePhotos);
		permissionModeratePhotos.setText(getApplicationContext().getResources()
				.getString(R.string.permission_moderate_photos)
				+ " "
				+ model.getPermissionModeratePhotos());

		TextView permissionModerateComments = (TextView) findViewById(R.id.permissionModerateComments);
		permissionModerateComments.setText(getApplicationContext()
				.getResources()
				.getString(R.string.permission_moderate_comments)
				+ " " + model.getPermissionModerateComments());

	}

}
