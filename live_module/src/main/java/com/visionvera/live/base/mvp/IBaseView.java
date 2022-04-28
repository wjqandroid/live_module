package com.visionvera.live.base.mvp;

/**
 * @Desc mvp模式view层基础接口
 * @Author yemh
 * @Date 2019/4/12 15:04
 */
public interface IBaseView {
    /**
     * 显示网络错误或其他错误，一般在RxJava中onError方法中回调
     */
    void showError(String errorMsg);

    enum ResultType{
        SUCCESS,
        FAIL
    }

}
