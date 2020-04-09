package com.example.mvp_frame.mvp.model;


import com.example.mvp_frame.app.App;
import com.example.mvp_frame.callback.IDataCallBack;
import com.example.mvp_frame.di.componend.DaggerRxOperateComponent;
import com.example.mvp_frame.mvp.model.api.ApiService;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RxJava和OkHttp关联的封装类,具体请请求数据
 */
public class RxOperateImpl {
    //获取ApiService对象
    @Inject
    ApiService mApiService;

    //生成RxOperateComponent类的对象
    public RxOperateImpl(){
        //将apiService注入到RxOpweateImpl里面
        DaggerRxOperateComponent.builder()
                .appComponent(App.daggerAppComponent())
                .build()
                .inject(this);
    }

    //没有参数的get请求
    //参数1：url get请求的url地址
    //参数2：dataCallBack结果的的回调（实际上就是接口回调）
    //<T> 就是接口回调获取的值
    public<T> void requestData(String url, IDataCallBack<T> dataCallBack){
        RxSchedulersOperator.retryWhenOperator(mApiService.requestData(url))
                .subscribe(getObserver(dataCallBack));

    }
    /**
     * 有参数的get请求
     *
     * @param url
     * @param params
     * @param dataCallBack dataCallBack结果的的回调（实际上就是接口回调）
     * @param <T>
     */
    public <T> void requestData(String url, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (params == null || params.size() == 0)
            requestFormData(url, dataCallBack);
        else
            RxSchedulersOperator.retryWhenOperator(mApiService.requestData(url,params)).
                    subscribe(getObserver(dataCallBack));
    }
    /**
     * get请求下载文件
     *
     * @param url
     * @param dataCallBack dataCallBack结果的的回调（实际上就是接口回调）
     * @param <T>
     */
    public<T> void downloadFile(String url, IDataCallBack<T> dataCallBack){
        RxSchedulersOperator.retryWhenOperator(mApiService.downloadFile(url))
                .subscribe(getObserver(dataCallBack));

    }
    /**
     * 没有参数的post请求
     *
     * @param url
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, IDataCallBack<T> dataCallBack) {
        RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url)).
                subscribe(getObserver(dataCallBack));

    }


    /**
     * 有参数的post请求
     *
     * @param url
     * @param params
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (params == null || params.size() == 0)
            requestFormData(url, dataCallBack);
        else
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url, params)).
                    subscribe(getObserver(dataCallBack));
    }


    /**
     * 有请求头并且有参数的post请求
     *
     * @param url
     * @param headers
     * @param params
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, Map<String, T> headers, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (headers == null || headers.size() == 0)  //请求头为空 但是参数不为空
            requestFormData(url, params, dataCallBack);
        else if ((headers == null || headers.size() == 0) && // 请求头和参数都为空
                (params == null || params.size() == 0)) {
            requestFormData(url, dataCallBack);
        } else
            //请求头和参数都不为空
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url, headers, params)).
                    subscribe(getObserver(dataCallBack));
    }
//

    /**
     * 抽取出结果回调的方法
     *
     * @param dataCallBack
     * @param <T>
     * @return
     */
    private <T> RxObserver<T> getObserver(IDataCallBack<T> dataCallBack) {
        return new RxObserver<T>(dataCallBack) {
            @Override
            public void onSubscribe(Disposable d) {
                if (dataCallBack != null)
                    dataCallBack.onResponseDisposable(d);

            }

            @Override
            public void onNext(T t) {
                if (dataCallBack != null)
                    dataCallBack.onStateSucess(t);
            }
        };
    }

}
