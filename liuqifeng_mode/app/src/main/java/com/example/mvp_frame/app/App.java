package com.example.mvp_frame.app;

import android.app.Activity;
import android.app.Application;
import android.os.Process;
import android.util.AndroidRuntimeException;


import com.example.mvp_frame.di.componend.DaggerAppComponent;
import com.example.mvp_frame.di.module.AppModule;

import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    //全局变量
    private static App mInstance;
    //通过set集合存储Activity
    private Set<Activity> mActivitier;
    //接收 DaggerAppComponent对象
    private static DaggerAppComponent mDaggerAppCompat;

    //赋值并对外提供出去
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initIngect();
    }

    //用来配置Dagger2
    private void initIngect() {
        if (mDaggerAppCompat == null)
            mDaggerAppCompat = (DaggerAppComponent) DaggerAppComponent.builder().
                    appModule(new AppModule(this)).build();
    }
    //把DaggerAppComponent对外提供出去
    public static DaggerAppComponent daggerAppComponent(){
        return mDaggerAppCompat;
    }

    //多线程的时候同步代码块对完提供出去
    public static synchronized App getInstance() {
        return mInstance;
    }

    //把所有的Activity放到一个集合通过App对象调用这个方法
    //把所有的Activity添加到这个集合方便统一管理统一退出
    public void addActivity(Activity act) {

        if (mActivitier == null)

            mActivitier = new HashSet<Activity>();

        mActivitier.add(act);

    }

    //遍历集合统一关闭所有Activity的方法，关闭程序
    public void removeActivity() {
        if (mActivitier != null) {
            for (Activity act : mActivitier) {
                act.finish();
            }
        }

        //把当前进程销毁
        android.os.Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
