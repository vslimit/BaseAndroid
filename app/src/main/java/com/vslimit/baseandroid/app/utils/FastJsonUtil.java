package com.vslimit.baseandroid.app.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by vslimit on 15/3/2.
 */
public class FastJsonUtil<T> {

    public T toModel(Class<T> clazz,String result) {
        return JSON.parseObject(result, clazz);
    }

    public List<T> toList(Class<T> clazz, String result) {
        return JSON.parseArray(result, clazz);
    }
}
