package com.example.mvp_frame.di.module;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.mvp_frame.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//提供SharedPreferences单例对象
@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSp() {
        return mApp.getSharedPreferences("config", Context.MODE_PRIVATE);
    }


}
