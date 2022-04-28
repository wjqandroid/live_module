package com.visionvera.live.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * @Desc 用于解决 沉浸式状态栏导致 adjustResize 失效问题
 *
 * @Author yemh
 * @Date 2019-08-23 13:59
 *
 */
public class WindowSoftModeAdjustResizeExecutor {
    public static void assistActivity(Activity activity) {
        new WindowSoftModeAdjustResizeExecutor(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private Activity mActivity;

    private WindowSoftModeAdjustResizeExecutor(Activity activity) {
        this.mActivity = activity;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int val = 0;
        int orientation = mActivity.getRequestedOrientation();
        if (orientation == 1) {
            val = 25;
        } else if (orientation == 2) {
            val = 0;
        }
        int viewH = (int) ScreenUtils.dp2px(mActivity, val);
        int usableHeightNow = computeUsableHeight() - viewH;
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;

            frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;

            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return (r.bottom - r.top);
        }
        return r.bottom;
    }
}
