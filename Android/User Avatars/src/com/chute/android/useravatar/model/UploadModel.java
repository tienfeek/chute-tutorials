package com.chute.android.useravatar.model;

public class UploadModel {
    @SuppressWarnings("unused")
    private static final String TAG = UploadModel.class.getSimpleName();

    private String assetId;
    private String url;

    public String getAssetId() {
	return assetId;
    }

    public void setAssetId(String assetId) {
	this.assetId = assetId;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

}
