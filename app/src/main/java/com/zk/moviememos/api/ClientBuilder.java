package com.zk.moviememos.api;

import com.zk.moviememos.App;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by zk <zkzxc1988@163.com>.
 */

public class ClientBuilder {

    public static final String CACHE_DIRECTORY_NAME = "responses";

    public static final int CONNECT_TIME_OUT = 30;

    public static final int READ_TIME_OUT = 30;

    private static OkHttpClient client;

    private ClientBuilder() {
        File cacheDirectory = new File(App.getContext().getCacheDir(), CACHE_DIRECTORY_NAME);
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheDirectory, cacheSize);

        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    public static class SingletonHolder {

        private static final ClientBuilder INSTANCE = new ClientBuilder();
    }

    public static ClientBuilder getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static OkHttpClient getClient() {
        return client;
    }

}
