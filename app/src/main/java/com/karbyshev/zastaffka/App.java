package com.karbyshev.zastaffka;

import android.app.Application;

import com.karbyshev.zastaffka.network.service.RetrofitService;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitService.init();
    }
}
