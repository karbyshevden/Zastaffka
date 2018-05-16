package com.karbyshev.zastaffka.presenter;


import com.karbyshev.zastaffka.base.PresenterBase;
import com.karbyshev.zastaffka.network.Request;
import com.karbyshev.zastaffka.view.MainActivityFragment;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class MainPresenter extends PresenterBase<MainContract.View> implements MainContract.Presenter{
    private final static String CLIENT_KEY = "19d6afefc92f592eb3a28f4b3b69d309cca64f90d1a35033ae45bb22814dc533";
    private static int PAGE_SIZE = 10;
    private Disposable photoRequest = Disposables.empty();

    @Override
    public void loadData(int page) {
        MainActivityFragment.isLoading = true;
        getMainView().adapterIsLoading(true);
        photoRequest = Request.getPhotos(CLIENT_KEY, page, PAGE_SIZE).subscribe(
                photoRequest -> {
                    getMainView().addAllToAdapter(photoRequest);
                    MainActivityFragment.isLoading = false;
                    getMainView().adapterIsLoading(false);
                }, error -> {
                    MainActivityFragment.isLoading = false;
                    getMainView().adapterIsLoading(true);
                });
    }

    @Override
    public void searchData(int page, String searchQuery) {
        getMainView().deleteAllFromAdapter();
        MainActivityFragment.isLoading = true;
        getMainView().adapterIsLoading(true);
        photoRequest = Request.searchPhotos(CLIENT_KEY, page, PAGE_SIZE, searchQuery).subscribe(
                searchRequest -> {
                    getMainView().addAllToAdapter(searchRequest.getResults());
                    MainActivityFragment.isLoading = false;
                    getMainView().adapterIsLoading(false);
                }, error -> {
                    MainActivityFragment.isLoading = false;
                    getMainView().adapterIsLoading(true);
                });
    }

    @Override
    public void viewIsReady() {
        getMainView().showIsConnectedStatement();
    }
}
