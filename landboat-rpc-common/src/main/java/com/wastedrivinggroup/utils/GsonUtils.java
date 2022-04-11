package com.wastedrivinggroup.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author chenqwwq
 * @date 2022/4/7
 **/
public class GsonUtils {
    private static final Gson gson = new Gson();

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
