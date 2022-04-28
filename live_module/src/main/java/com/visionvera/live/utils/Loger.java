package com.visionvera.live.utils;

import android.util.Log;

/**
 * @Desc Log 工具类
 * @Author yemh
 * @Date 2019/4/12 14:55
 */
public class Loger {
    private static final String TAG = "心理c端-live";
    private static final boolean DEBUG = true;

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String msg, Exception e) {
        if (DEBUG) {
            Log.e(TAG, msg + "  " + e.toString());
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }
}
