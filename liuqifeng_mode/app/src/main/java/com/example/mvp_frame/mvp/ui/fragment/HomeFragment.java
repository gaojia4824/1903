package com.example.mvp_frame.mvp.ui.fragment;

import android.util.Log;

import com.example.mvp_frame.R;
import com.example.mvp_frame.base.BaseFragment;
import com.example.mvp_frame.base.BasePresenter;
import com.example.mvp_frame.base.IBasePresenter;
import com.example.mvp_frame.base.IBaseView;
import com.example.mvp_frame.mvp.presenter.HomePresenter;
import com.example.mvp_frame.mvp.ui.common.LazyFragment;

import androidx.annotation.NonNull;

//实现懒加载，继承LazyFragment懒加载类，懒加载类继承了BaseFragment。
public class HomeFragment extends LazyFragment {

    private int mType;

    public HomeFragment(int type) {
        this.mType = type;
    }

    //重写父类LazyFragment停止开始加载数据的方法
    @Override
    protected void stopLazyLoad() {
        //循环判断哪个Fragmenr停止了
        switch (mType) {
            case 0:
                Log.e("TAG", "HomeFragment类的第一个Fragment停止加载");
                break;
            case 1:
                Log.e("TAG", "HomeFragment类的第二个Fragment停止加载");
                break;
            case 2:
                Log.e("TAG", "HomeFragment类的第三个Fragment停止加载");
                break;
            case 3:
                Log.e("TAG", "HomeFragment类的第四个Fragment停止加载");
                break;
        }
    }

    //重写父类LazyFragment开始加载数据的方法，向P层发送指令加载数据
    @Override
    protected void lazyLoad() {
        getmPresenter().start(mType);
    }

    //用来注册注入Dagger2对象的方法
    @Override
    protected void initInject() {

    }

    //创建HomeFragmentPresenter对象关联P层
    @Override
    protected BasePresenter createPresenter() {
        return new HomePresenter();
    }

    //Fragment可见的时候加载数据，向P层发送请求数据的指令
    ////要把索引值传到Fragment里边，要根据不同的索引值加载不同的数据，所以需要调用start把索引值传进去
    //**如果实现懒加载的话就不用了这个方法了，把里边执行代码写到lazyLoad()加载数据的方法。
//    @Override
//    public void onStart() {
//        super.onStart();
//        getmPresenter().start(mType);
//    }


    //加载Fragment布局，根据type类型来判断加载不同的布局
    @Override
    protected int getLayoutId() {
        Integer layoutId = switchLayout(mType);
        if (layoutId != null) {
            return layoutId;
        }
        return 0;
    }

    //封装加载布局的方法
    private static Integer switchLayout(int mType) {
        switch (mType) {
            case 0:
                return R.layout.fragment_home;
            case 1:
                return R.layout.fragment_daohang;
            case 2:
                return R.layout.fragment_tixi;
            case 3:
                return R.layout.fragment_gongzhonghao;
        }
        return null;
    }

    @Override
    public void stateScouess(Object o) {

    }

    @Override
    public void stateError(String msg) {

    }
}
