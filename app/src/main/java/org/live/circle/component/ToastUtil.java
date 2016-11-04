package org.live.circle.component;

import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import org.live.circle.LiveCircleApp;


public class ToastUtil {
	private static Toast toast;

	public static void showToast(String str) {
		if (TextUtils.isEmpty(str)) {
			return;
		}
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {//消失的很慢，why？
			android.widget.Toast toast = android.widget.Toast.makeText(LiveCircleApp.sContext, str, android.widget.Toast.LENGTH_LONG);
			toast.show();
			return;
		}
        if (toast == null) {
        	toast = Toast.makeText(LiveCircleApp.sContext, str, Toast.LENGTH_LONG);
        } else {
        	toast.setText(str);
        }
		toast.show();
	}
}
