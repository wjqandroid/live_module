package com.visionvera.live.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.visionvera.library.BaseApplication;


/**
 * @Desc SharedPreference 存储工具类
 * @Author yemh
 * @Date 2019/4/12 14:49
 */
public class SharedPreferenceUtils {

    private static SharedPreferences.Editor editor;
    private static SharedPreferences settings;

    /**
     * 存储一个boolean值
     *
     * @param key
     * @param value
     * @return
     */
    public static void putBoolean(String key, boolean value) {
        getEd().putBoolean(key, value).commit();
    }

    /**
     * 获取一个boolean值
     *
     * @param key
     * @param defValue
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getSP().getBoolean(key, defValue);
    }

    /**
     * 存储一个int型数值
     *
     * @param key
     * @param value
     * @return
     */
    public static void putInt(String key, int value) {
        getEd().putInt(key, value).commit();
    }

    /**
     * 获取一个int型数值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String key, int defValue) {
        return getSP().getInt(key, defValue);
    }

    /**
     * 存储一个long型数值
     *
     * @param key
     * @param value
     * @return
     */
    public static void putLong(String key, long value) {
        getEd().putLong(key, value).commit();
    }

    /**
     * 获取一个long型数值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(String key, long defValue) {
        return getSP().getLong(key, defValue);
    }

    /**
     * 存储一个String型数值
     *
     * @param key
     * @param value
     * @return
     */
    public static void putString(String key, String value) {
        getEd().putString(key, value).commit();
    }

    /**
     * 获取一个String型数值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        return getSP().getString(key, defValue);
    }

    /**
     * 获取一个SharedPreferences对象
     *
     * @return
     */
    public static SharedPreferences getSP() {
        if (settings == null) {
            settings = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        }
        return settings;
    }

    /**
     * 获取一个SharedPreferences.Editor对象
     *
     * @return
     */
    public static SharedPreferences.Editor getEd() {
        if (editor == null) {
            editor = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance()).edit();
        }
        return editor;
    }

}
