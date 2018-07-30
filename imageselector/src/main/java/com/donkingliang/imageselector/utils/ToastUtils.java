package com.donkingliang.imageselector.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wisn on 2017/10/5.
 */

public class ToastUtils {

    private static Toast toast;

    public static void show(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void show(Context context ,int id) {
        show(context,context.getString(id));
    }

}
