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

import android.content.Context;
import android.widget.Toast;

public class NotificationUtil {

  public static final String TAG = NotificationUtil.class.getSimpleName();

  private NotificationUtil() {
  }

  public static void makeToast(Context context, int resId) {
    makeToast(context, context.getString(resId));
  }

  public static void makeToast(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
  }

  public static void makeServerErrorToast(Context context) {
    makeToast(context, R.string.http_error);
  }

  public static void makeServerErrorToast(Context context, String message) {
    makeToast(context, message + ", " + context.getString(R.string.http_error));
  }

  public static void makeConnectionProblemToast(Context context) {
    makeToast(context, R.string.http_exception);
  }

  public static void makeConnectionProblemToast(Context context, String message) {
    makeToast(context, message + ", " + context.getString(R.string.http_exception));
  }

  public static void makeParserErrorToast(Context context) {
    makeToast(context, R.string.parsing_exception);
  }

  public static void makeParserErrorToast(Context context, String message) {
    makeToast(context, message + ", " + context.getString(R.string.parsing_exception));
  }
}
