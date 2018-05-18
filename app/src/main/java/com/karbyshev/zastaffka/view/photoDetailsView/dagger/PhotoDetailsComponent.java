package com.karbyshev.zastaffka.view.photoDetailsView.dagger;

import com.karbyshev.zastaffka.view.photoDetailsView.PhotoDetailsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {PhotoDetailsModule.class})
@Singleton
public interface PhotoDetailsComponent {

        void inject(PhotoDetailsFragment presenter);
}
