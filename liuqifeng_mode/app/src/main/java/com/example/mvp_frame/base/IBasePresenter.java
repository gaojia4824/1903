package com.example.mvp_frame.base;


//P层接口
public interface IBasePresenter<T> {
    //P层两个重载方法
    //可以在BasePresenter类中重写这两个方法，在HomeFragmentPresenter可以想用哪个就调用那个
    void  start();
    void start(T t);
    //为什么会写两个方法呢，
    // 就是说我们后边Fragment复用的时候得传过来一个泛型（一个int类型的type）
    //在P层的state的方法通过这个int类型的type来判断属于哪个fragment，
    //所有在这里写两个重载的方法（一个有参数的一个无参数的）
    //T其实就是int类型的type，T代表任何一个对象，下边参数（state(T t)）加T了，上边泛型（IBasePresenter<T>）也要加T不然就报错了
}
