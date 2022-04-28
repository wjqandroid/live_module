package com.visionvera.live.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @Desc 基础工具类
 * @Author yemh
 * @Date 2019/5/22 10:28
 */
public class BaseUtils {
    private static Context mContext;

    /**
     * 获取软件版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        mContext = context;
        String versionName = null;
        try {
            // 获取软件版本名称，对应AndroidManifest.xml下android:versionCode
            versionName = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取设备版本号
     *
     * @return
     */
    public static int getSystemVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 正则表达式判断密码格式是否正确(字母,数字,符号必须选2)
     * @param val
     * @return
     */
    public static boolean matchPassWrod(String val) {
        //判断密码是否包含数字：包含返回1，不包含返回0
        int i = val.matches(".*\\d+.*") ? 1 : 0;

        //判断密码是否包含字母：包含返回1，不包含返回0
        int j = val.matches(".*[a-zA-Z]+.*") ? 1 : 0;

        //判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
        int k = val.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;

        //判断密码长度是否在6-16位
        int l = val.length();

        if (i + j + k < 2 || l < 6 || l > 16) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 正则表达式判断手机号
     * @param phone
     * @return
     */
    public static boolean matchPhone(String phone) {
        return phone.matches("^(1[3-9])\\d{9}$");
    }
}
