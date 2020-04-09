package com.example.mvp_frame.base;

import android.widget.ImageView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

//P层的基类
//1.关联V层生命周期（为什么要关联V层生命周期呢，是为了避免内存泄漏）
//2.网络开关容器存储所有网络开关（也是为了避免内存泄漏）

//因为父接口（IBasePresenter<T>）泛型带T，所以IBasePresenter<T>要把T带上
//P层持有V层引用，以为P层在获取完对数据后要交给V层来更新UI，所以在（BasePresenter<V extends IBaseView<T>, T>）中用V继承IBaseView,V代表IBaseView的实现类；
//因为父接口（IBaseView<T>）泛型带T，所以IBaseView<T>要把T带上

//可以在这个类中重写这两个方法（void  state();void state(T t)），在HomeFragmentPresenter可以想用哪个就调用那个
public abstract class BasePresenter<V extends IBaseView<T>, T> implements IBasePresenter<T> {

    //弱引用对象
    private WeakReference<V> mWr;
    //添加开关容器
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void start() {

    }

    @Override
    public void start(T t) {

    }

    //他来判断用弱引用修饰V层引用，给弱引用对象赋值
    public void attachView(V view) {
        if (mWr == null) {
            mWr = new WeakReference<V>(view);
        }
    }

    //把v层弱引用清空并且把所有网络开关断开
    public void detachView() {
        if (mWr != null) {
            mWr.clear();
            mWr = null;
        }
        deleteDisposable();
    }

    //添加网络开关,把网络开关添加到容器
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
            mCompositeDisposable.add(disposable);
        }
    }

    //删除关闭网络开关
    private void deleteDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }


}
