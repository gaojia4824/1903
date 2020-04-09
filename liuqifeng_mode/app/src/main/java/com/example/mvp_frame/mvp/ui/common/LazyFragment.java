package com.example.mvp_frame.mvp.ui.common;

import android.os.Bundle;
import android.view.View;

import com.example.mvp_frame.base.BaseFragment;
import com.example.mvp_frame.base.BasePresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//切换Fragment有两种方法：第一种.add（只是说把Fragment添加到show里边了）hide（把Fragment隐藏了）show（把Fragment0显示），第二种.replace（）
//两种方式的区别，如果在Activity执行是第一种方法的话，那么必然会执行Fragment中的onHiddenChanged这个方法，他直接关系着切换Fragment的时候数据能不能请求出来
//如果在Activity执行是第二种方法的话，会执行Fragment的生命周期方法（11个），必然会执行onreate

//Fragment懒加载类，是为了结局Viewpager预加载，将ViewPager预加载关闭，继承BaseFragment
public abstract class LazyFragment extends BaseFragment {

    //定义两个boolean类型的变量,
    //Fragment是否初始化了
    private boolean mInitview = false;
    //Fragment对用户是否可见
    private boolean mHasLoadMore = false;

    //调用replace（）会执行Fragment的onStart方法
    @Override
    public void onStart() {
        super.onStart();
    }

    //他是调用父类BaseFragment的onViewCreated方法
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //设置初始化，代表Fragment加载的这个视图已经被初始化了
        mInitview = true;
        //处理业务逻辑，加载数据的方法
        initLazyLoad();

    }

    //以为懒加载的话会用到这个方法,为了兼容低版本的手机要用这个过期的方法
    //他是在当前Fragment结合viewPager用的时候才会调用这个方法；
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //处理业务逻辑，加载数据的方法
        initLazyLoad();
    }

    //加载数据的方法,着个方法不能是抽象的，因为要处理逻辑
    protected void initLazyLoad() {
        //进行判断，如果Fragment的Viwe没被初始化，那么就跳出这个方法
        if (!mInitview)
            return;

        //Fragment对用户可见
        if (getUserVisibleHint()) {
            lazyLoad();
            //代表加载完数据了
            mHasLoadMore = true;
            //Fragment代表对用户不可见//表示当前Fragment对用户可见了，如果可见了执行加载数据的方法
        } else {
            //如果有数据了，才停止加载 //else 表示当前Fragment对用户不可见,如果不可见就停止加载数据
            if (mHasLoadMore) {
                stopLazyLoad();
            }
        }
    }

    //  else 表示当前Fragment对用户不可见,如果不可见就停止加载数据
    protected abstract void stopLazyLoad();

    //表示当前Fragment对用户可见了，如果可见了执行加载数据的方法
    protected abstract void lazyLoad();

    //因为是调用父类BaseFragment的方法不能删，所以把mVisibleToUser和mInitview制为false
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHasLoadMore=false;
        mInitview=false;
    }

    //当执行Fragment的hide（）show（）的时候会调用这个方法
    //Fragment的隐藏状态发生变化了 hidden=true表示Fragment隐藏了，hidden=false表示Fragment显示了
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    //因为在这个类里用不到这个方法，所有把这个方法设置成抽象方法
    @Override
    protected abstract void initInject();

    //因为懒加载的类里不需要创建P层对象，因为这个类是实现懒加载的，所以不需要创建P层对象，所以也改为抽象类
    @Override
    protected abstract BasePresenter createPresenter();

    //因为这个方法是加载布局Id的所以也用不到，也改为抽象方法
    @Override
    protected abstract int getLayoutId();

    //这个返回成功还是失败的方法也用不到，所以也改为抽象方法；
    @Override
    public abstract void stateScouess(Object o);

    @Override
    public abstract void stateError(String msg);
}
