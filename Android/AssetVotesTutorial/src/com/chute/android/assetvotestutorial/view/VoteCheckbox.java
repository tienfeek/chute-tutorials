package com.chute.android.assetvotestutorial.view;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chute.android.assetvotestutorial.R;
import com.chute.android.assetvotestutorial.model.VotesSingleton;
import com.chute.sdk.v2.api.votes.GCVotes;
import com.chute.sdk.v2.api.votes.VotesMap;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.VoteModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class VoteCheckbox extends CheckBox {

    private VotesMap votes;
    private VotesObserver votesObserver;

    public VoteCheckbox(Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    public VoteCheckbox(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	init();
    }

    public VoteCheckbox(Context context) {
	super(context);
	init();
    }

    public static final String TAG = VoteCheckbox.class.getSimpleName();
    private String assetId;
    private String albumId;

    private void init() {
	if (isInEditMode() == false) {
	    this.setBackgroundDrawable(null);
	}
	this.setButtonDrawable(R.drawable.vote_selector);
	this.setOnCheckedChangeListener(new OnVoteChangedListener());
	final VotesSingleton votes = VotesSingleton.getInstance(getContext());
	votesObserver = new VotesObserver();
	votes.addObserver(votesObserver);
	votes.getVotes();
    }

    public void deleteObservers() {
	try {
	    VotesSingleton.getInstance(getContext()).deleteObserver(votesObserver);
	} catch (Exception e) {
	    Log.w(TAG, "", e);
	}
    }

    public void markHeartByAssetId(String albumId, String assetId) {
	this.assetId = assetId;
	this.albumId = albumId;
	Log.d(TAG, "assetId " + assetId);
	if (votes == null) {
	    return;
	}
	if (votes.get(Integer.parseInt(assetId)) != null) {
	    this.setChecked(true);
	} else {
	    this.setChecked(false);
	}

    }

    private final class OnVoteChangedListener implements OnCheckedChangeListener {
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    Log.d(TAG, "isChecked " + isChecked);
	    AssetModel asset = new AssetModel();
	    asset.setId(assetId);
	    AlbumModel album = new AlbumModel();
	    album.setId(albumId);
	    GCVotes.vote(getContext(), album, asset, new VotesCreateCallback()).executeAsync();
	    if (votes == null) {
		return;
	    }
	    int id = Integer.parseInt(assetId);
	    if (isChecked) {
		votes.append(id, true);
	    } else {
		votes.delete(id);
	    }
	}
    }
    
    private final class VotesCreateCallback implements HttpCallback<ResponseModel<VoteModel>> {

		@Override
		public void onSuccess(ResponseModel<VoteModel> responseData) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			// TODO Auto-generated method stub
			
		}
    	
    }

    private final class VotesObserver implements Observer {
	@Override
	public void update(Observable observable, Object data) {
	    VoteCheckbox.this.votes = (VotesMap) data;
	}
    }
}
