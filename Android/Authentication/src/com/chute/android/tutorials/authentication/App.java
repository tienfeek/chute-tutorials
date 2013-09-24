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
package com.chute.android.tutorials.authentication;

import com.chute.sdk.v2.api.Chute;
import com.chute.sdk.v2.api.authentication.AuthConstants;

import android.app.Application;

public class App extends Application {

  private static final String CLIENT_SECRET = "0599436c911d8ee27d34d26c2dde73a1a342a8a0e0b20592ef00f90fe1ca5305";
  private static final String CLIENT_ID = "4f15d1f138ecef6af9000004";

  @Override
  public void onCreate() {
    super.onCreate();
    Chute.init(getApplicationContext(), new AuthConstants(CLIENT_ID, CLIENT_SECRET));
  }
}
