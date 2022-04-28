package com.visionvera.live.share;

import android.app.Activity;
//
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.media.UMWeb;

/**
 * @Desc 分享工具类
 *
 * @Author yemh
 * @Date 2019/6/10 15:15
 *
 */
public class ShareUtils {
    private Activity mContext;
    private ShareHandler mHandler;

    public ShareUtils(Activity context){
        this.mContext = context;
    }

    /**为方便参数的传递，把需要的参数封装为一个shareBean
     * @param shareBean 分享参数类
     */
    public void beginShare(ShareBean shareBean, ShareHandler shareHandler) {
//        mHandler = shareHandler;
//        UMWeb web = new UMWeb(shareBean.getUrl());
//        web.setTitle(shareBean.getTitle());
//        web.setDescription(shareBean.getDesc());
//        web.setThumb(new UMImage(mContext, shareBean.getThumb()));
//        new ShareAction(mContext)
//                .withMedia(web)
//                .setPlatform(shareBean.getShareMedia())
//                .setCallback(shareListener)
//                .share();
    }

//    public UMShareListener shareListener = new UMShareListener() {
//        /**
//         * @descrption 分享开始的回调
//         * @param platform 平台类型
//         */
//        @Override
//        public void onStart(SHARE_MEDIA platform) {
//        }
//
//        /**
//         * @descrption 分享成功的回调
//         * @param platform 平台类型
//         */
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            if (mHandler != null)
//                mHandler.shareSuccess();
//        }
//
//        /**
//         * @descrption 分享失败的回调
//         * @param platform 平台类型
//         * @param t 错误原因
//         */
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            if (mHandler != null)
//                mHandler.shareFailed(t.getMessage());
//        }
//
//        /**
//         * @descrption 分享取消的回调
//         * @param platform 平台类型
//         */
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            if (mHandler != null)
//                mHandler.shareCancle();
//        }
//    };
}
