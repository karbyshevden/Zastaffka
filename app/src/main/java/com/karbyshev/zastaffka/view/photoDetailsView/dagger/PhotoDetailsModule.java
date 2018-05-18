package com.karbyshev.zastaffka.view.photoDetailsView.dagger;

import com.karbyshev.zastaffka.presenter.PhotoDetailPresenter;
import com.karbyshev.zastaffka.presenter.PhotoDetailsContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoDetailsModule {

    @Provides
    @Singleton
    public PhotoDetailsContract.Presenter provideMainPresenter() {
        return new PhotoDetailPresenter();
    }
}
