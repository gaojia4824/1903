package com.example.mvp_frame.base;

import android.os.Bundle;

import com.example.mvp_frame.app.App;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//如果Activity不需要加载数据就不用实现IBaseView接口，重写两个方法
public abstract class BaseActivity<T> extends AppCompatActivity implements IBaseView<T> {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局的方法
        int layoutId = getLayout();
        if (layoutId != 0) {
            setContentView(layoutId);
            //在app中的集合又来添加Activity
            App.getInstance().addActivity(this);
            //绑定ButtonKnife
            unbinder = ButterKnife.bind(this);
            //初始化Activity里的一些控件
            onViewCreated();
            //设置监听器的方法
            initListenner();
        }
    }
    //设置处理监听器的方法
    protected abstract void initListenner();

    //初始化Activity里的一些控件
    protected abstract void onViewCreated();

    //加载布局的方法
    protected abstract int getLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        //关闭集合
        App.getInstance().removeActivity();
    }

    //如果Activity不需要加载数据就不用实现IBaseView接口，重写两个方法
    @Override
    public void stateScouess(T t) {

    }

    @Override
    public void stateError(String msg) {

    }
}
