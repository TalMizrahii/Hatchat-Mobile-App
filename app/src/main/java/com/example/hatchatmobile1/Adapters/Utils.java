package com.example.hatchatmobile1.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.widget.Toast;

public class Utils {

    public static void showShortToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * Converting an image to be rounded in and in to a fixed size.
     *
     * @param bitmap The image's bitmap.
     * @return The image rounded.
     */
    public static Bitmap getCircleBitmap(Bitmap bitmap, int diameter) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, diameter, diameter, false);
        Bitmap circularBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(circularBitmap);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, diameter, diameter);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(diameter / 2.0f, diameter / 2.0f, diameter / 2.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        int left = (diameter - scaledBitmap.getWidth()) / 2;
        int top = (diameter - scaledBitmap.getHeight()) / 2;
        canvas.drawBitmap(scaledBitmap, left, top, paint);

        return circularBitmap;
    }
}


