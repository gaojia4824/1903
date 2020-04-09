package com.example.mvp_frame.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;


//他是一个View层的实现类
//继承Fragmengt实现IBaseView，在BaseFragment中添加V层和P层引用
//在BaseFragment<V extends IBaseView, P extends IBasePresenter<T>, T>，分别继承V层和P层，V代表V层，P代表P层
//因为父接口（IBasePresenter<T>）泛型带T，所以IBasePresenter<T>要把T带上
//因为父接口（IBaseView）泛型带T，所以IBaseView要把T带上

//V层对象持有P层引用， 作用：1.拿到P层对象向P层发送请求数据指令 （让具体的实现类去请求对应的收据）
// 2.拿到P层对象关联V层生命周期  3.拿到P层对象释放V层引用
public abstract class BaseFragment<P extends BasePresenter, T>
        extends Fragment implements IBaseView<T> {

    //获得P层对象
    private P mPresenter;
    //获得v层对象
    // private  V mView;
    private Unbinder unbinder;


    //在onCreateView中创建P层对象,加载布局判断layout不能为null，不能为零，因为布局默认就是零，如果是零就什么都不显示
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建布局id
        int layoutId = getLayoutId();
        View view = null;
        if (layoutId != 0)
            view = inflater.inflate(layoutId, container, false);
        return view;
    }

    //创建ButterKnife对象,P层和V层进行关联
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //绑定ButtonKnife
        unbinder = ButterKnife.bind(this, view);
        if (mPresenter == null)
            //创建P层对象
            mPresenter = createPresenter();

        //2.P层关联V层的生命周期
        mPresenter.attachView(this);

        //if (mView == null)
        //mView = creeateView();

        initInject();
    }

    //用来存放Dagger2代码，用Dagger2的时候，在每个Fragment后每个Activity初始化Dagger2的一些配置
    protected abstract void initInject();

    // protected abstract V creeateView();

    protected abstract P createPresenter();

    //把V层对外提供出去
    // public V getmView(){
    // if(mView!=null)
    //  return mView;
    //return null;
    //}
    //V层持有P层引用，而P层是私有的，所以把P层对外提供出去
    public P getmPresenter() {
        if (mPresenter != null)
            return mPresenter;
        return null;
    }

    //创建布局Id的方法
    protected abstract int getLayoutId();

    //关闭ButterKnife解绑
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        //3.P层释放掉V层引用
        if (mPresenter != null)
            mPresenter.detachView();
            mPresenter = null;
    }
}
