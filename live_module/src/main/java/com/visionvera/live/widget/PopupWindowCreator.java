package com.visionvera.live.widget;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.visionvera.live.R;


/**
 * @Desc popWindow构造器
 * @Author yemh
 * @Date 2019/4/26 13:35
 */
public class PopupWindowCreator extends PopupWindow {
    private int screenWidth;
    private Activity activity;
    private ObjectAnimator inAnim;
    private View parentView, layoutView;

    public PopupWindowCreator(Activity context, int tag) {
        super.setFocusable(true);
        this.activity = context;
        if (tag == 0) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            screenWidth = wm.getDefaultDisplay().getWidth();
            // 设置popWindow的显示和消失动画
            setAnimationStyle(R.style.ver_pop_anim_style);

            super.setWidth(screenWidth);
        } else {
            // 设置popWindow的显示和消失动画
            setAnimationStyle(R.style.hor_pop_anim_style);
        }
        parentView = activity.getWindow().getDecorView()
                .findViewById(android.R.id.content);
    }

    public void setPopView(View view, int tag) {
        this.layoutView = view;
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        layoutView.measure(width, height);
        super.setContentView(layoutView);
        if (tag == 0) {
            super.setHeight(layoutView.getMeasuredHeight());
        } else {
            super.setWidth(500);
            super.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        }
        super.setOutsideTouchable(true);
    }

    /**
     * 显示pop(ver)
     */
    public void verShow() {
        if (!isShowing()) {
            update();
            int width = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int height = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            parentView.measure(width, height);
            showAtLocation(parentView, Gravity.BOTTOM, 0,
                    -parentView.getMeasuredHeight());
            setAlph(0.6f);
        } else {
            dismiss();
        }
    }

    /**
     * 隐藏pop(ver)
     */
    public void verDismiss() {
        if (isShowing()) {
            super.dismiss();
            setAlph(1.0f);
        }
    }

    /**
     * 显示pop(hor)
     */
    public void horShow() {
        if (!isShowing()) {
            update();
            int width = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int height = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            parentView.measure(width, height);
            showAtLocation(parentView, Gravity.RIGHT, 0, 0);
        } else {
            dismiss();
        }
    }

    /**
     * 隐藏pop(hor)
     */
    public void horDismiss() {
        super.dismiss();
    }

    /**
     * pop弹出设置背景半透明
     *
     * @param val
     */
    public void setAlph(float val) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = val;
        window.setAttributes(lp);
    }
}
