package com.karbyshev.zastaffka.view.mainView.dagger;

import com.karbyshev.zastaffka.view.mainView.MainActivityFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {MainModule.class})
@Singleton
public interface MainComponent {

        void inject(MainActivityFragment presenter);
}
