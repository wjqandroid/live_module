package com.visionvera.live.network.factory;


import com.visionvera.library.BaseApplication;
import com.visionvera.live.network.LoggingInterceptor;
import com.visionvera.live.utils.Loger;
import com.visionvera.live.utils.NetWorkUtils;
import com.visionvera.psychologist.account_module.util.AccountManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Desc 初始化OkHttpClient
 *
 * 使用枚举创建单例
 * @Author yemh
 * @Date 2019/4/15 17:21
 */
public enum OKHttpClient {


    INSTANCE;

    private final OkHttpClient okHttpClient;
    private OkHttpClient.Builder okHttpBuilder;
    //TODO 填写自己的包名
    public static final String CACHE_NAME = "com.visionvera.psychologist.live";

    private static final int TIMEOUT_READ = 30;
    private static final int TIMEOUT_WRITE = 30;
    private static final int TIMEOUT_CONNECTION = 30;

    OKHttpClient() {
        okHttpBuilder = new OkHttpClient.Builder();

        /**
         * 设置缓存
         */
        File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), CACHE_NAME);
        Cache cache = new Cache(cacheFile, 50 * 1024 * 1024);

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.isNetworkAvailable()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (!NetWorkUtils.isNetworkAvailable()) {
                    int maxAge = 0;
                    // 无网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader(CACHE_NAME)// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 有网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader(CACHE_NAME)
                            .build();
                }
                return response;
            }
        };
        okHttpBuilder.cache(cache).addInterceptor(cacheInterceptor);

        /**
         * 设置头信息
         */
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
//                String authToken = UserManager.getInstence().getAuthToken();
//                String userId = UserManager.getInstence().getUserId();

                String authToken =  AccountManager.getInstence().getGetAccountInfo().token;
                String userId =  AccountManager.getInstence().getGetAccountInfo().userId;

                Request request=chain.request().newBuilder()
                        .addHeader("userId", userId)
                        .addHeader("token", authToken)
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(originalRequest.method(), originalRequest.body()) .build();

                return chain.proceed(request);
            }
        };
        okHttpBuilder.addInterceptor(headerInterceptor);

        /**
         * 打印请求Log
         */
       /* HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Loger.d(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(loggingInterceptor);*/

        //添加自定义log拦截器
        okHttpBuilder.addInterceptor(new LoggingInterceptor());

        /**
         * 设置超时和重新连接
         */
        okHttpBuilder.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS);

        /**
         * 失败重连
         */
        okHttpBuilder.retryOnConnectionFailure(true);
        okHttpClient = okHttpBuilder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
