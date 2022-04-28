package com.visionvera.live.module.home.bean;


import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * arouter传递object需要的辅助类
 */
@Route(path = "/live_module/json")
public class JsonServiceImpl implements SerializationService {

    private Gson mGson;

    @Override
    public void init(Context context) {
        mGson=new Gson();
    }

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        checkJson();
        return mGson.fromJson(input,clazz);
    }

    @Override
    public String object2Json(Object instance) {
        checkJson();
        return mGson.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        checkJson();
        return mGson.fromJson(input,clazz);
    }

    public void checkJson() {
        if (mGson == null) {
            mGson = new Gson();
        }
    }
}
