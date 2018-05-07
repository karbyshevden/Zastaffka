package com.karbyshev.zastaffka.view;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.karbyshev.zastaffka.adapter.MainAdapter;

public interface IMainActivity {

    void startLoad(MainAdapter mMainAdapter, int page);
}
