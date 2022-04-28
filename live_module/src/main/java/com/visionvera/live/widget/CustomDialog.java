package com.visionvera.live.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


/**
 * @Desc 自定义dialog
 * @Author yemh
 * @Date 2019/4/28 17:49
 */
@SuppressLint("InflateParams")
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public void disMiss() {
        cancel();
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private String positive;
        private String negative;
        private OnClickListener positiveListener;
        private OnClickListener negativeListener;
        private LayoutInflater mInflater;

        public Builder(Context context) {
            this.mContext = context;
            if (mInflater == null) {
                mInflater = LayoutInflater.from(mContext);
            }
        }

        /**
         * 设置dialog内容
         */
        public Builder setMessage(int id) {
            this.mMessage = mContext.getText(id).toString();
            return this;
        }

        /**
         * 设置dialog内容
         */
        public Builder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        /**
         * 设置dialog标题
         */
        public Builder setTitle(int id) {
            this.mTitle = mContext.getText(id).toString();
            return this;
        }

        /**
         * 设置dialog标题
         */
        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        /**
         * 设置dialog布局文件
         */
        public Builder setContentView(View v) {
            return this;
        }

        /**
         * 设置肯定按钮文字及按钮监听
         */
        public Builder setPositiveButton(int id, OnClickListener listener) {
            this.positive = mContext.getText(id).toString();
            this.positiveListener = listener;
            return this;
        }

        /**
         * 设置肯定按钮文字及按钮监听
         */
        public Builder setPositiveButton(String text, OnClickListener listener) {
            this.positive = text;
            this.positiveListener = listener;
            return this;
        }

        /**
         * 设置否按钮文字及按钮监听
         */
        public Builder setNegativeButton(int id, OnClickListener listener) {
            this.negative = mContext.getText(id).toString();
            this.negativeListener = listener;
            return this;
        }

        /**
         * 设置否按钮文字及按钮监听
         */
        public Builder setNegativeButton(String text,
                                         OnClickListener listener) {
            this.negative = text;
            this.negativeListener = listener;
            return this;
        }

        /**
         * 设置肯定按钮文字及按钮监听
         */
        public Builder setPositiveText(int id) {
            this.positive = mContext.getText(id).toString();
            return this;
        }

        /**
         * 设置肯定按钮文字及按钮监听
         */
        public Builder setPositiveText(String text) {
            this.positive = text;
            return this;
        }

        /**
         * 设置否按钮文字及按钮监听
         */
        public Builder setNegativeText(int id) {
            this.negative = mContext.getText(id).toString();
            return this;
        }

        /**
         * 设置否按钮文字及按钮监听
         */
        public Builder setNegativeText(String text) {
            this.negative = text;
            return this;
        }

    }
}
