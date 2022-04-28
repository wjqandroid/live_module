package com.visionvera.live.service;


import com.visionvera.live.listener.IDialogListener;

/**
 * @Desc 全局dialog工具类
 *
 * @Author yemh
 * @Date 2019-08-12 15:27
 *
 */
public class DialogUtils {
    private static IDialogListener mListener;

    private DialogUtils() {

    }

    private static class DialogUtilsHolder{
        private static DialogUtils mInstance = new DialogUtils();
    }

    public void setListener(IDialogListener listener) {
        this.mListener = listener;
    }

    public static DialogUtils getInstance() {
        return DialogUtilsHolder.mInstance;
    }


    public void showDialog() {
        if (mListener != null) {
            mListener.show();
        }
    }

    public void cancel() {
        if (mListener != null) {
            mListener.cancel();
        }
    }
}
