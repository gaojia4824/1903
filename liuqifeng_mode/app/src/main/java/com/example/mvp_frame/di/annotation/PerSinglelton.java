package com.example.mvp_frame.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

//局部单例加Scope，然后加Retention指定一下他什么时候运行时编译
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerSinglelton {
}
