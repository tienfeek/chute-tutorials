/**
 * The MIT License (MIT)

Copyright (c) 2013 Chute

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.chute.android.assetvotestutorial.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AssetVotesPreferences {

  private static final String KEY_ASSET_ID_HEARTS = "key_asset_id_hearts_";
  public static final String TAG = AssetVotesPreferences.class.getSimpleName();
  private final SharedPreferences prefs;

  private AssetVotesPreferences(Context context) {
    prefs = context.getSharedPreferences("assetvotestutorial", Context.MODE_PRIVATE);
  }

  static AssetVotesPreferences instance;

  public static AssetVotesPreferences get() {
    return instance;
  }

  public static void init(Context context) {
    if (instance == null) {
      instance = new AssetVotesPreferences(context.getApplicationContext());
    }
  }

  public void clear() {
    prefs.edit().clear().commit();
  }

  protected final <T> void setPreference(final String key, final T value) {
    SharedPreferences.Editor edit = prefs.edit();
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

  public void heartAssetById(String assetId) {
    setPreference(KEY_ASSET_ID_HEARTS + assetId, true);
  }

  public void unheartAssetById(String assetId) {
    prefs.edit().remove(KEY_ASSET_ID_HEARTS + assetId).commit();
  }

  public boolean isAssetHeartById(String assetId) {
    return prefs.contains(KEY_ASSET_ID_HEARTS + assetId);
  }
}
