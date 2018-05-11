package com.karbyshev.zastaffka.presenter;

import com.karbyshev.zastaffka.adapter.MainAdapter;

public interface IMainPresenter {

    void loadData(MainAdapter mMainAdapter, int page);

    void searchData(MainAdapter mMainAdapter, int page, String searchQuery);
}
