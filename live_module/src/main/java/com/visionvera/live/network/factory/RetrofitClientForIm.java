package com.visionvera.live.network.factory;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.visionvera.live.network.HttpInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @Desc 初始化Retrofit(for im)
 *
 * 使用枚举创建单例
 * @Author yemh
 * @Date 2019/4/15 17:12
 */
public enum RetrofitClientForIm {
    INSTANCE;

    private final Retrofit retrofit;

    RetrofitClientForIm() {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OKHttpClient.INSTANCE.getOkHttpClient())
                //设置baseUrl
                .baseUrl(HttpInterface.IM_BASE_URL)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())

                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .setLenient()
                                .serializeNulls()
                                .create()))
                //增加返回值为Oservable<T>的支持(RxJava)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //创建
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
