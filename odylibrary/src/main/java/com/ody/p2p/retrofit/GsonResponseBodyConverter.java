package com.ody.p2p.retrofit;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by pzy on 2017/7/20.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;


    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();
        try {
            return gson.fromJson(response, type);
        } catch (Exception e) {
            //抛一个自定义ResultException 和信息
            throw new JsonResultException(response);
        }
    }
}