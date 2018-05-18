package com.karbyshev.zastaffka.view.mainView.dagger;

import com.karbyshev.zastaffka.presenter.MainContract;
import com.karbyshev.zastaffka.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    @Singleton
    public MainContract.Presenter provideMainPresenter() {
        return new MainPresenter();
    }
}
