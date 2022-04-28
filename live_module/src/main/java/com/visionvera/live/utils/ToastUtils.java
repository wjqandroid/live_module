package com.visionvera.live.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @Desc Toast工具类
 * @Author yemh
 * @Date 2019/4/12 16:29
 */

public class ToastUtils {

    /**
     * 短时间toast来自文本
     *
     * @param context
     * @param text
     */
    public static void showShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间toast来自资源id
     *
     * @param context
     * @param resId
     */
    public static void showShort(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间toast来自文本
     *
     * @param context
     * @param text
     */
    public static void showLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间toast来自资源id
     *
     * @param context
     * @param resId
     */
    public static void showLong(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }
}
