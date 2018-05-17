package com.karbyshev.zastaffka.presenter;

import com.karbyshev.zastaffka.adapter.MainAdapter;
import com.karbyshev.zastaffka.base.MvpPresenter;
import com.karbyshev.zastaffka.base.MvpView;
import com.karbyshev.zastaffka.models.Photo;

import java.util.List;

public interface MainContract {

    interface View extends MvpView {

        void showNoConnectionStatement();

        void showIsConnectedStatement();

        void addAllToAdapter(List<Photo> list);

        void isLoading(boolean adapterIsLoading, boolean fragmentIsLoading);

        void deleteAllFromAdapter();
    }

    interface Presenter extends MvpPresenter<View> {

        void loadData(int page);

        void searchData(int page, String searchQuery);
    }
}
