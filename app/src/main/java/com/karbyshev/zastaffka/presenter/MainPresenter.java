package com.karbyshev.zastaffka.presenter;


import com.karbyshev.zastaffka.adapter.MainAdapter;
import com.karbyshev.zastaffka.network.Request;
import com.karbyshev.zastaffka.view.IMainView;
import com.karbyshev.zastaffka.view.MainActivity;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class MainPresenter implements IMainPresenter{
    private final static String CLIENT_KEY = "19d6afefc92f592eb3a28f4b3b69d309cca64f90d1a35033ae45bb22814dc533";
    private static int PAGE_SIZE = 10;
    private Disposable photoRequest = Disposables.empty();

    @Override
    public void loadData(MainAdapter mMainAdapter, int page) {
        MainActivity.isLoading = true;
        mMainAdapter.setLoading(true);
        photoRequest = Request.getPhotos(CLIENT_KEY, page, PAGE_SIZE).subscribe(
                photoRequest -> {
                    mMainAdapter.addAll(photoRequest);
                    MainActivity.isLoading = false;
                    mMainAdapter.setLoading(false);
                }, error -> {
                    MainActivity.isLoading = false;
                    mMainAdapter.setLoading(false);
                });
    }
}
