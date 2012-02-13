package com.chute.android.imageloadertutorial.app;

import com.chute.android.imageloadertutorial.R;
import com.chute.android.imageloadertutorial.adapter.LoadChutePhotosAdapter;
import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCHttpRequestParameters;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class LoadChutePhotosActivity extends Activity {

	public static final String TAG = LoadChutePhotosActivity.class
			.getSimpleName();
	private GridView grid;
	private LoadChutePhotosAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_layout);
		
		grid = (GridView) findViewById(R.id.photoGrid);
		
//		String url = "http://sharedroll.com/hwcybf/";
		String url = "http://chutedemos.heroku.com";
		GCChutes.search(getApplicationContext(), url,
				new GCChuteCollectionCallback()).executeAsync();
	}
	
	private final class GCChuteCollectionCallback implements GCHttpCallback<GCChuteCollection> {

		@Override
		public void onSuccess(GCChuteCollection responseData) {
			adapter = new LoadChutePhotosAdapter(LoadChutePhotosActivity.this, responseData);
			grid.setAdapter(adapter);
		}

		@Override
		public void onHttpException(GCHttpRequestParameters params,
				Throwable exception) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onHttpError(int responseCode, String statusMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onParserException(int responseCode, Throwable exception) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
