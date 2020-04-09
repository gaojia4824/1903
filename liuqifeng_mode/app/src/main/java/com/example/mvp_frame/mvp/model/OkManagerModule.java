package com.example.mvp_frame.mvp.model;


import com.example.mvp_frame.mvp.model.api.ApiService;
import com.example.mvp_frame.mvp.model.api.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.http.Streaming;

//需要对外提供Okhttp对象
//dater2写法
@Module
public class OkManagerModule {
    //dater2写法
    @Provides
    @Streaming
    public OkHttpClient.Builder provideOkBuilder(){
        //添加Interceptor对象,connectTimeout设置连接超时,单位秒（TimeUnit.SECONDS）,readTimeout读取链接超时

        return new OkHttpClient.Builder()
                //添加拦截器.addInterceptor
                //.addInterceptor(new TokenHeaderIncepter())
                .connectTimeout(6,TimeUnit.SECONDS)
                .readTimeout(6,TimeUnit.SECONDS);
    }

    //提供Okhttpclient对象，把Okhttpclient注入到HomePresenter类
    @Provides
    @Streaming
    public OkHttpClient provideOkClient(){
        return  provideOkBuilder().build();

    }

    //把RetrofitBuilder的Builder对象提供出去
    //获取接口Constants类的url接口
    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder().baseUrl(Constants.BASE_URL);
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        OkHttpClient okHttpClient = provideOkClient();
        return provideRetrofitBuilder().client(okHttpClient).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build();
    }


}
