//package com.visionvera.live.utils;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.blankj.utilcode.util.LogUtils;
////import com.tencent.imsdk.TIMCallBack;
////import com.tencent.imsdk.TIMLogLevel;
////import com.tencent.imsdk.TIMManager;
////import com.tencent.imsdk.TIMSdkConfig;
////import com.tencent.imsdk.TIMUserConfig;
////import com.tencent.imsdk.TIMUserStatusListener;
//import com.visionvera.live.constant.Globe;
//import com.visionvera.live.service.DialogUtils;
//
//public class ImUtils {
//
//    /**
//     * 初始化腾讯IM
//     */
//    public static void initTecentIm(Context context){
//        // TODO: 2020/2/6  TIMLogLevel正式使用需要关闭
//
//        TIMSdkConfig config = new TIMSdkConfig(Globe.SDK_APPID)
//                .enableLogPrint(true)
//                .setLogLevel(TIMLogLevel.DEBUG)
//                .setLogPath(FileUtil.LogCachePath);
//
//        TIMManager.getInstance().init(context, config);
//
//        forceUserConfig();
//    }
//
//    /**
//     * 用户配置信息
//     */
//    private static void forceUserConfig(){
//        TIMUserConfig userConfig = new TIMUserConfig()
//                .setUserStatusListener(new TIMUserStatusListener() {
//                    @Override
//                    public void onForceOffline() {
//                        LogUtils.a("被踢了");
//                        DialogUtils.getInstance().showDialog();
//                    }
//
//                    @Override
//                    public void onUserSigExpired() {
//                        LogUtils.a("用户签名过期了");
//                    }
//                });
//        TIMManager.getInstance().setUserConfig(userConfig);
//    }
//
//    /**
//     * 登录腾讯IM
//     */
//    public static void loginTecentIm(int chatterId) {
//        String userSign = SharedPreferenceUtils.getString(Globe.SP_USER_SIGN, "");
//        if (!TextUtils.isEmpty(userSign)) {
//            TIMManager.getInstance().login(chatterId + "", userSign, new TIMCallBack() {
//                @Override
//                public void onError(int code, String desc) {
//                    LogUtils.a("登录腾讯IM失败 code: " + code + " errmsg: " + desc);
//                    //离线被踢
//                    if (code == 6208){
//                        DialogUtils.getInstance().showDialog();
//                    }
//                }
//
//                @Override
//                public void onSuccess() {
//                    LogUtils.a("登录腾讯IM成功");
//                }
//            });
//        }
//    }
//
//    /**
//     * 登出腾讯IM
//     */
//    public static void loginOutTecentIm() {
//        String userSign = SharedPreferenceUtils.getString(Globe.SP_USER_SIGN, "");
//        if (!TextUtils.isEmpty(userSign)) {
//            TIMManager.getInstance().logout(new TIMCallBack() {
//                @Override
//                public void onError(int code, String desc) {
//                    LogUtils.a("登出腾讯IM失败 code: " + code + " errmsg: " + desc);
//                }
//
//                @Override
//                public void onSuccess() {
//                    LogUtils.a("登出腾讯IM成功");
//                }
//            });
//        }
//    }
//}
