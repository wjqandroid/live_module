package com.visionvera.live.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.visionvera.library.BaseApplication;


/**
 * @Desc 网络使用工具类
 * @Author yemh
 * @Date 2019/4/12 14:56
 */
public class NetWorkUtils {

    /**
     * 判断网络是否可用
     *
     * @return boolean
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否是wifi网络 此方法更可靠
     *
     * @return boolean
     */
    public static boolean isWiFiActive() {
        Context mContext = BaseApplication.getInstance().getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI")
                            && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}