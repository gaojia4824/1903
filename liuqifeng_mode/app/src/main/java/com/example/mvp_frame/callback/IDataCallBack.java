package com.example.mvp_frame.callback;

import io.reactivex.disposables.Disposable;
//接口回调
public interface IDataCallBack<T> {
    //成功的方法
    void onStateSucess(T t);
    //失败的方法
    void onStateError(String msg);
    //网络开关
    void onResponseDisposable(Disposable disposable);
}
