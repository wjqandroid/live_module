package com.visionvera.live.share;

/**
 * @Desc 分享回调
 *
 * @Author yemh
 * @Date 2019/6/10 15:12
 */
public interface ShareHandler {
    /**
     * 分享成功
     */
    void shareSuccess();

    /**
     * 分享失败
     *
     * @param msg
     */
    void shareFailed(String msg);

    /**
     * 取消分享
     */
    void shareCancle();
}
