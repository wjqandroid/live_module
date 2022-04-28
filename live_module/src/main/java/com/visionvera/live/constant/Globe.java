package com.visionvera.live.constant;

import android.content.Context;

/**
 * @Desc 常量相关
 * @Author yemh
 * @Date 2019/4/12 14:19
 */
public class Globe {

    /************************* 常量部分 ***************************/
    /**
     * 屏幕宽度
     */
    public static int screenWidth = 0;

    /**
     * 屏幕高度
     */
    public static int screenHeight = 0;

    /**
     * 开发环境
     */
    public static int DEVELOP = 1;

    /**
     * 测试环境
     */
    public static int TEST = 2;

    /**
     * 预发布环境(线上)
     */
    public static int CHECK = 3;

    /**
     * 友盟appkey
     */
    public final static String UM_APPKEY = "5cf8bb310cafb2d9d6000dee";

    /**
     * 友盟appsecret
     */
    public final static String UM_APPSECRET = "e7eb7c0d17f12e42a9a65f3da166d2c0";

    /**
     * 腾讯IM SDK appid
     */
    public final static int SDK_APPID = 1400219765;

    public static Context mNowContext;

    /*************** 存储在数据库或者sp中的键部分 ***************/
    /**
     * 屏幕宽度key
     */
    public static String SP_SCREENWIDTH = "screenWidth";

    /**
     * 屏幕高度key
     */
    public static String SP_SCREENHEIGHT = "screenHeight";

    /**
     * 是否第一次运行
     */
    public static String SP_FIRSTRUN = "firstRun";

    /**
     * 是否已经登录
     */
    public static String SP_ISLOGIN = "isLogin";

    /**
     * IM UserSign
     */
    public static String SP_USER_SIGN = "userSign";

    /************************* 网络请求返回类型 ***************************/
    /**
     * 网络错误
     */
    public static final int NET_ERROR = 1;
    /**
     * 正常返回
     */
    public static final int SERVER_RESULT = 2;
}
