package com.example.mvp_frame.base;

//view接口，主要用来更新UI，获取数据
public interface IBaseView <T>{
    //V层接口返回成功方法
    void  stateScouess(T t);
    //V层接口返回失败方法
    void stateError(String msg);

    //T其实就是int类型的type，T代表任何一个对象，下边参数（state(T t)）加T了，上边泛型（IBasePresenter<T>）也要加T不然就报错了

}
