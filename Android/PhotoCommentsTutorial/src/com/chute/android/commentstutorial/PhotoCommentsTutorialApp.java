package com.chute.android.commentstutorial;

import com.chute.android.comments.app.PhotoCommentsApp;
import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;

public class PhotoCommentsTutorialApp extends PhotoCommentsApp {

  public static final String TAG = PhotoCommentsTutorialApp.class.getSimpleName();

  /* Test credentials */
  final String APP_ID = "4f3c39ff38ecef0c89000003";
  final String APP_SECRET = "c9a8cb57c52f49384ab6117c4f6483a1a5c5a14c4a50d4cef276a9a13286efc9";
  final String TOKEN = "f7f1a31c46f95f4085956ae146aa0f3eec1874a9d17ec07de5e22d7c7340da0e";

  @Override
  public void onCreate() {
    super.onCreate();
    Chute.init(getApplicationContext(), new AuthConstants(APP_ID, APP_SECRET), TOKEN);
  }
}
