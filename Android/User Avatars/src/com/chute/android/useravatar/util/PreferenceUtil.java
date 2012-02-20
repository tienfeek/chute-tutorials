package com.chute.android.useravatar.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {
	public static final String TAG = PreferenceUtil.class.getSimpleName();
	private final Context context;

	private PreferenceUtil(Context context) {
		this.context = context;
	}

	static PreferenceUtil instance;

	public static PreferenceUtil get() {
		return instance;
	}

	public static void init(Context context) {
		if (instance == null) {
			instance = new PreferenceUtil(context.getApplicationContext());
		}
	}

	public SharedPreferences getPreferenceManager() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	private final <T> void setPreference(final String key, final T value) {
		SharedPreferences.Editor edit = getPreferenceManager().edit();

		if (value.getClass().equals(String.class)) {
			edit.putString(key, (String) value);
		} else if (value.getClass().equals(Boolean.class)) {
			edit.putBoolean(key, (Boolean) value);
		} else if (value.getClass().equals(Integer.class)) {
			edit.putInt(key, (Integer) value);
		} else if (value.getClass().equals(Long.class)) {
			edit.putLong(key, (Long) value);
		} else if (value.getClass().equals(Float.class)) {
			edit.putFloat(key, (Float) value);
		} else {
			throw new UnsupportedOperationException(
					"Need to add a primitive type to shared prefs");
		}
		edit.commit();
	}

}
