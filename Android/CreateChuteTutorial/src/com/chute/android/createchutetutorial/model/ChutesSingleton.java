package com.chute.android.createchutetutorial.model;

import java.util.HashMap;
import java.util.Observable;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.utils.GCConstants;

public class ChutesSingleton extends Observable {
	@SuppressWarnings("unused")
	private static final String TAG = ChutesSingleton.class.getSimpleName();

	private static ChutesSingleton instance;
	public final GCChuteCollection collection;
	private final HashMap<String, Boolean> refreshAssetsMap = new HashMap<String, Boolean>();
	private final Context context;

	private ChutesSingleton(Context context) {
		super();
		this.context = context;
		collection = new GCChuteCollection();
		refreshChutes();
	}

	public static synchronized ChutesSingleton getInstance(Context context) {
		if (instance == null) {
			instance = new ChutesSingleton(context);
		}
		return instance;
	}

	public void refreshChutes() {
		GCChutes.all(context, GCConstants.CURRENT_USER_ID,
				new ChuteCollectionCallback()).executeAsync();
	}

	public void refreshAssets() {
		for (int i = 0; i < collection.size(); i++) {
			if (refreshAssetsMap.containsKey(collection.get(i).getId())) {
				collection.get(i)
						.assets(context, new AssetCollectionCallback())
						.executeAsync();
			}
		}
		refreshAssetsMap.clear();
		setChanged();
		notifyObservers();
	}

	public void addChute(GCChuteModel model) {
		collection.add(model);
		setChanged();
		notifyObservers();
	}

	private class ChuteCollectionCallback implements
			GCHttpCallback<GCChuteCollection> {

		@Override
		public void onSuccess(GCChuteCollection responseData) {
			collection.clear();
			collection.addAll(responseData);
			for (GCChuteModel chute : collection) {
				refreshAssetsMap.put(chute.getId(), true);
			}
			refreshAssets();
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
		}
	}

	private final class AssetCollectionCallback implements
			GCHttpCallback<GCChuteModel> {
		@Override
		public void onSuccess(GCChuteModel responseData) {
			setChanged();
			notifyObservers();
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
		}
	}

}
