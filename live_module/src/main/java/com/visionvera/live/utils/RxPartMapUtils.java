package com.visionvera.live.utils;

import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Desc String, File转requestBody
 *
 * @Author yemh
 * @Date 2019/4/22 16:12
 */
public class RxPartMapUtils {

    public static RequestBody toRequestBodyOfText(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public static RequestBody toRequestBodyOfImage(File file) {
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        return body;
    }

    public static RequestBody toRequestBodyOfMap(Map<String, String> value) {
        String json = new Gson().toJson(value);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return body;
    }

    public static MultipartBody.Part toMultipartBodyOfImage(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return body;
    }

    public static RequestBody toRequestBodyOfString(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), value);
        return body;
    }
}
