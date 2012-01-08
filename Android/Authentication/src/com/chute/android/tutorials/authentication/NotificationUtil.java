package com.chute.android.tutorials.authentication;

import android.content.Context;
import android.widget.Toast;

public class NotificationUtil {
    @SuppressWarnings("unused")
    private static final String TAG = NotificationUtil.class.getSimpleName();

    private NotificationUtil() {
    }

    public static void makeToast(Context context, int resId) {
	makeToast(context, context.getString(resId));
    }

    public static void makeToast(Context context, String message) {

	/*LayoutInflater inflater = LayoutInflater.from(context);
	View layout = inflater.inflate(R.layout.toast, null, false);
	ImageView image = (ImageView) layout.findViewById(R.id.image);
	image.setImageResource(R.drawable.icon);
	TextView text = (TextView) layout.findViewById(R.id.text);
	text.setText(message);
	text.setTypeface(AppUtil.getTypefaceAllerRegular(context));

	Toast toast = new Toast(context);
	// toast.setView(layout);
	toast.setText(message);
	toast.setDuration(Toast.LENGTH_LONG);
	toast.show();*/
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
