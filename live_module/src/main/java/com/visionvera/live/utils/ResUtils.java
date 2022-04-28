package com.visionvera.live.utils;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.visionvera.library.BaseApplication;


/**
 * @Desc 资源工具类
 * @Author yemh
 * @Date 2019/4/12 14:58
 */
public class ResUtils {

    /**
     * 获取色值
     * @param color
     * @return
     */
    public static int getColor(int color) {
        return ContextCompat.getColor(BaseApplication.getInstance(), color);
    }

    /**
     * 获取drawable
     * @param drawableId
     * @return
     */
    public static Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(BaseApplication.getInstance(), drawableId);
    }
}
