package com.karbyshev.zastaffka.presenter;

import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.karbyshev.zastaffka.adapter.MainAdapter;
import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.network.Request;
import com.karbyshev.zastaffka.view.IMainActivity;


import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class MainPresenter implements IMainActivity{
    private final static String CLIENT_KEY = "19d6afefc92f592eb3a28f4b3b69d309cca64f90d1a35033ae45bb22814dc533";
    private static int PAGE = 1;
    private static int PAGE_SIZE = 30;
    private boolean isLoadingInProgress = false;

    private Disposable photoRequest = Disposables.empty();

    @Override
    public void startLoad(MainAdapter mMainAdapter) {

    }

    private void loadMore(MainAdapter mMainAdapter){
        photoRequest = Request.getPhotos(CLIENT_KEY, PAGE, PAGE_SIZE).subscribe(
                photoRequest -> {
                    mMainAdapter.addAll(photoRequest);
                }, error -> {
                    //Some error
                });
    }
}
