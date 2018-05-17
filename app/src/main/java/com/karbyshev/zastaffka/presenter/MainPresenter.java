package com.karbyshev.zastaffka.presenter;


import com.karbyshev.zastaffka.base.PresenterBase;
import com.karbyshev.zastaffka.network.Request;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

import static com.karbyshev.zastaffka.common.Constants.CLIENT_KEY;

public class MainPresenter
        extends PresenterBase<MainContract.View>
        implements MainContract.Presenter{
    private static int PAGE_SIZE = 10;
    private Disposable photoRequest = Disposables.empty();

    @Override
    public void loadData(int page) {
        getView().isLoading(true, true);
        photoRequest = Request.getPhotos(CLIENT_KEY, page, PAGE_SIZE).subscribe(
                photoRequest -> {
                    getView().addAllToAdapter(photoRequest);
                    getView().isLoading(false, false);
                }, error -> {
                    getView().isLoading(true, false);
                });
    }

    @Override
    public void searchData(int page, String searchQuery) {
        getView().deleteAllFromAdapter();
        getView().isLoading(true, true);
        photoRequest = Request.searchPhotos(CLIENT_KEY, page, PAGE_SIZE, searchQuery).subscribe(
                searchRequest -> {
                    getView().addAllToAdapter(searchRequest.getResults());
                    getView().isLoading(false, false);
                }, error -> {
                    getView().isLoading(true, false);
                });
    }

    @Override
    public void viewIsReady() {
        getView().showIsConnectedStatement();
    }
}
