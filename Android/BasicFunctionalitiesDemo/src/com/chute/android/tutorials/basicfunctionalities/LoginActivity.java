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
package com.chute.android.tutorials.basicfunctionalities;

import com.chute.android.tutorials.authentication.BaseLoginActivity;

import android.content.Intent;

public class LoginActivity extends BaseLoginActivity {

  public static final String TAG = LoginActivity.class.getSimpleName();

  @Override
  public void launchMainAppActivity() {
    /**
     * This method will be responsible for handling the authentication success
     * or if the user was previously authenticated sucessfully.
     */
    final Intent intent = new Intent(getApplicationContext(),
        BasicFunctionalitiesDemoActivity.class);
    startActivity(intent);
    LoginActivity.this.finish();
  }
}
