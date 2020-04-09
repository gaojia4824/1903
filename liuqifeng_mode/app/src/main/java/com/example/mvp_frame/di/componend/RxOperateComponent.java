package com.example.mvp_frame.di.componend;


import com.example.mvp_frame.di.annotation.PerSinglelton;
import com.example.mvp_frame.mvp.model.RxOperateImpl;

import dagger.Component;

@PerSinglelton
@Component(dependencies = AppComponent.class)
public interface RxOperateComponent {
    //直接注入到RxOperateImpl类
    void inject(RxOperateImpl rxOperate);
}
