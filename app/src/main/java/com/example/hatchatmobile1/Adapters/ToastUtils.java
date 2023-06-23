package com.example.hatchatmobile1.Adapters;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static void showShortToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}


