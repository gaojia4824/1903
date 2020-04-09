package com.example.mvp_frame.di.componend;


import com.example.mvp_frame.di.annotation.PerSinglelton;
import com.example.mvp_frame.mvp.presenter.HomePresenter;
import dagger.Component;

@PerSinglelton
@Component(dependencies = AppComponent.class)
public interface HomeComponent {
    //专门纯粹的注入到HomePresenter里边
    void inject(HomePresenter homePresenter);
}
