package com.ody.p2p.retrofit;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.ody.p2p.BuildConfig;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.utils.GsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Administrator on 2016/11/30.
 */
public class RetrofitHelper {
    public String BASE_URL = OdyApplication.BASE_URL;
    public static final String TAG = "HTTP_INFO";
    private static final int DEFAULT_TIMEOUT = 15;
    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    NetWorkApi netWorkApi;


    public RetrofitHelper(String url) {
        //手动创建一个OkHttpClient并设置超时时间


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Log.i(TAG, "url:" + request.url());
                Log.i(TAG, "url:" + "request headers" + new GsonBuilder().disableHtmlEscaping().create().toJson(request.headers()));

                long t1 = System.nanoTime();
                Response response = chain.proceed(request);
                long t2 = System.nanoTime();

                double time = (t2 - t1) / 1e6d;
                Log.i(TAG, "time:" + time);
                Log.i(TAG, "code:" + response.code());

                return response;
            }
        });

        Retrofit.Builder b = new Retrofit.Builder()
                .client(builder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url);

        retrofit = b.build();
    }

    public RetrofitHelper() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                RequestBody body = request.body();
                Log.i(TAG, "url:" + request.url());
                Log.i(TAG, "url:" + "request headers" + new GsonBuilder().disableHtmlEscaping().create().toJson(request.headers()));
                long t1 = System.nanoTime();
                Response response = chain.proceed(request);
                long t2 = System.nanoTime();

                double time = (t2 - t1) / 1e6d;
                Log.i(TAG, "time:" + time);
                Log.i(TAG, "code:" + response.code());

                return response;
            }
        });

        Retrofit.Builder b = new Retrofit.Builder()
                .client(builder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ResponseConverterFactory.create(GsonUtils.buildGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL);

        retrofit = b.build();
    }

    public NetWorkApi getCategoryService() {

        return retrofit.create(NetWorkApi.class);
    }


}