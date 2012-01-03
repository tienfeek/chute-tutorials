package com.chute.android.useravatar.parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chute.android.useravatar.model.UploadModel;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

public class UploadParser implements GCHttpResponseParser<ArrayList<UploadModel>> {
    @SuppressWarnings("unused")
    private static final String TAG = UploadParser.class.getSimpleName();

    @Override
    public ArrayList<UploadModel> parse(String responseBody) throws JSONException {
	JSONArray array = new JSONArray(responseBody);
	ArrayList<UploadModel> list = new ArrayList<UploadModel>();
	for (int i = 0; i < array.length(); i++) {
	    UploadModel model = new UploadModel();
	    JSONObject obj = array.getJSONObject(i);
	    model.setAssetId(obj.getString("assetId"));
	    model.setUrl(obj.getString("url"));
	    list.add(model);
	}
	return list;
    }
}
