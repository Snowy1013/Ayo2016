package com.ayo.opensource.zpartial_explode;

import android.content.res.Resources;

/**
 * Created by azz on 15/11/19.
 */
public class PartialExplodeUtils {
    /**
     * 密度
     */
    public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2px(int dp) {
        return Math.round(dp * DENSITY);
    }

//    public static Bitmap createBitmapFromView(View view) {
//        view.clearFocus();
//        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
//        if (bitmap != null) {
//            synchronized (sCanvas) {
//                Canvas canvas = sCanvas;
//                canvas.setBitmap(bitmap);
//                view.draw(canvas);
//                canvas.setBitmap(null);
//            }
//        }
//        return bitmap;
//    }
//
//    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
//        try {
//            return Bitmap.createBitmap(width, height, config);
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//            if (retryCount > 0) {
//                System.gc();
//                return createBitmapSafely(width, height, config, retryCount - 1);
//            }
//            return null;
//        }
//    }
}
