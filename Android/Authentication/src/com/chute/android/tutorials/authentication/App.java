package com.chute.android.tutorials.authentication;

import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;

import android.app.Application;

public class App extends Application{
	
	private static final String CLIENT_SECRET = "0599436c911d8ee27d34d26c2dde73a1a342a8a0e0b20592ef00f90fe1ca5305";
	private static final String CLIENT_ID = "4f15d1f138ecef6af9000004";


	@Override
	public void onCreate() {
		super.onCreate();
		Chute.init(getApplicationContext(), new AuthConstants(CLIENT_ID, CLIENT_SECRET));
	}
}
