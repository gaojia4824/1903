package com.example.mvp_frame.mvp.presenter;

import android.util.Log;

import com.example.mvp_frame.app.App;
import com.example.mvp_frame.base.BasePresenter;
import com.example.mvp_frame.di.componend.DaggerHomeComponent;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import okhttp3.OkHttpClient;

//他是P层的实现类继承了BasePresenter，重写顶层（IBasePresenter）的两个方法
//可以在BasePresenter类中重写这两个方法，在这里可以想用哪个就调用那个
public class HomePresenter extends BasePresenter {

    //获取AppComponent类提供的okhttpclient对象
    @Inject
    OkHttpClient okHttpClient;

    //获取AppComponent类提供的inject对象，通过构造把AppComponent类的inject方法初始化
    public HomePresenter(){
        DaggerHomeComponent.builder()
                .appComponent(App.daggerAppComponent())
                .build()
                .inject(this);
    }

    //要把索引值传到Fragment里边，要根据不同的索引值加载不同的数据，所以需要调用start把索引值传进去
    //通过这个方法向M层请求数据
    @Override
    public void start(Object obj) {
        super.start(obj);
        //打印一下okhttoduixiang
        Log.e("TAG",okHttpClient.toString());
        if (obj instanceof Integer) {
            //将obj强转成integer类型
            Integer type = (Integer) obj;

            //通过switch判断加载不同的数据
            switch (type) {
                case 0:
                    Log.e("TAG","HomePresenter类的第一个Fragment开始加载数据了");
                    break;
                case 1:
                    Log.e("TAG","HomePresenter类的第二个Fragment开始加载数据了");
                    break;
                case 2:
                    Log.e("TAG","HomePresenter类的第三个Fragment开始加载数据了");
                    break;
                case 3:
                    Log.e("TAG","HomePresenter类的第四个Fragment开始加载数据了");
                    break;
            }
        }
    }
}
