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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chute_description_activity);
		
		ChuteDescriptionActivityIntentWrapper wrapper = new ChuteDescriptionActivityIntentWrapper(getIntent());
		GCChuteModel model = wrapper.getChuteModel();
		
		TextView chuteId = (TextView) findViewById(R.id.chuteId);
		chuteId.setText("ID : " + model.getId());
		
		TextView name = (TextView) findViewById(R.id.name);
		name.setText("Name : " + model.getName());
		
		TextView parcelId = (TextView) findViewById(R.id.parcelId);
		parcelId.setText("Parcel ID : " + model.getRecentParcelId());
		
		TextView userId = (TextView) findViewById(R.id.userId);
		userId.setText("User ID : " + model.getRecentUserId());
		
		TextView password = (TextView) findViewById(R.id.password);
		password.setText("Password : " + model.getPassword());
		
		TextView user = (TextView) findViewById(R.id.user);
		user.setText("User : " + model.getUser().toString());
		
		TextView membersCount = (TextView) findViewById(R.id.membersCount);
		membersCount.setText("Members : " + model.getMembersCount());
		
		TextView contributorsCount = (TextView) findViewById(R.id.contributorsCount);
		contributorsCount.setText("Contributors : " + model.getContributorsCount());
		
		TextView recentCount = (TextView) findViewById(R.id.recentCount);
		recentCount.setText("Recent : " + model.getRecentCount());
		
		TextView createdAt = (TextView) findViewById(R.id.createdAt);
		createdAt.setText("Created at : " + model.getCreatedAt());
		
		TextView updatedAt = (TextView) findViewById(R.id.updatedAt);
		updatedAt.setText("Updated at : " + model.getUpdatedAt());
		
		TextView assetCount = (TextView) findViewById(R.id.assetsCount);
		assetCount.setText("Assets : " + model.getAssetsCount());
		
		TextView thumbUrl = (TextView) findViewById(R.id.thumbUrl);
		thumbUrl.setText("Thumbnail url : " + model.getRecentThumbnailURL());
		
		TextView shortcut = (TextView) findViewById(R.id.shortcut);
		shortcut.setText("Shortcut : " + model.getId());
		
		TextView permissionView = (TextView) findViewById(R.id.permissionView);
		permissionView.setText("Permission view : " + model.getPermissionView());
		
		TextView permissionAddMembers = (TextView) findViewById(R.id.permissionAddMembers);
		permissionAddMembers.setText("Permission to add members : " + model.getPermissionAddMembers());
		
		TextView permissionAddPhotos = (TextView) findViewById(R.id.permissionAddPhotos);
		permissionAddPhotos.setText("Permission to add photos : " + model.getPermissionAddPhotos());
		
		TextView permissionAddComments = (TextView) findViewById(R.id.permissionAddComments);
		permissionAddComments.setText("Permission to add comments : " + model.getPermissionAddComments());

		TextView permissionModerateMembers = (TextView) findViewById(R.id.permissionModerateMembers);
		permissionModerateMembers.setText("Permission to moderate members : " + model.getPermissionModerateMembers());
		
		TextView permissionModeratePhotos = (TextView) findViewById(R.id.permissionModeratePhotos);
		permissionModeratePhotos.setText("Permission to moderate photos : " + model.getPermissionModeratePhotos());
		
		TextView permissionModerateComments = (TextView) findViewById(R.id.permissionModerateComments);
		permissionModerateComments.setText("Permission to moderate comments : " + model.getPermissionModerateComments());
	
	}
	
	
}
