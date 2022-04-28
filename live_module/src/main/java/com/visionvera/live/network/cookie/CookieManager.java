package com.visionvera.live.network.cookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @Desc ok3的cookie管理器
 * @Author yemh
 * @Date 2019/4/15 17:10
 */
public class CookieManager implements CookieJar {

    private static Context mContext;

    private static PersistentCookieStore cookieStore;

    public CookieManager(Context context) {
        mContext = context;
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(mContext);
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie cookie : cookies) {
                cookieStore.add(url, cookie);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        //返回的List<Cookie>会被添加进请求。
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
