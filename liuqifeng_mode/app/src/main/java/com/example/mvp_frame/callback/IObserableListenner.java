package com.example.mvp_frame.callback;

//给RxSchedulersOperator设置接口回调
public interface IObserableListenner<T> {
    void getObserable1Data(T t);
}
