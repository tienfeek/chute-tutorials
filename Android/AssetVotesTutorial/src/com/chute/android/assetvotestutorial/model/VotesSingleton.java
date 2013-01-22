package com.chute.android.assetvotestutorial.model;

import java.util.Observable;

import com.chute.sdk.v2.api.votes.GCVotes;
import com.chute.sdk.v2.api.votes.VotesMap;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.VoteModel;
import com.chute.sdk.v2.model.requests.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

import android.content.Context;

public class VotesSingleton extends Observable {

	public static final String TAG = VotesSingleton.class.getSimpleName();
	private static VotesSingleton instance;
	private final Context context;
	private AssetModel responseData = new AssetModel();
	private AlbumModel album = new AlbumModel();
	private AssetModel asset = new AssetModel();
	
	private VotesMap map;

	private VotesSingleton(Context context) {
		super();
		this.context = context;
		refreshVotes();
	}

	public static synchronized VotesSingleton getInstance(Context context) {
		if (instance == null) {
			instance = new VotesSingleton(context.getApplicationContext());
		}
		return instance;
	}

	public void refreshVotes() {
		album.setId("1946");
		asset.setId("5858");
		GCVotes.get(context, album, asset, new VotesGetCallback())
				.executeAsync();
	}

	public void getVotes() {
		if (map != null) {
			setChanged();
			notifyObservers(map);
		} else {
			refreshVotes();
		}
	}

	private final class VotesGetCallback implements
			HttpCallback<ResponseModel<VoteModel>> {

		@Override
		public void onSuccess(ResponseModel<VoteModel> responseData) {
			String assetId = responseData.getData().getAssetId();
			map.append(Integer.parseInt(assetId), true);
//			VotesSingleton.this.responseData.setId(responseData.getData().getAssetId());
			setChanged();
//			notifyObservers(VotesSingleton.this.responseData);
			notifyObservers(map);

		}

		@Override
		public void onHttpError(ResponseStatus responseCode) {
			// TODO Auto-generated method stub

		}

	}

}
