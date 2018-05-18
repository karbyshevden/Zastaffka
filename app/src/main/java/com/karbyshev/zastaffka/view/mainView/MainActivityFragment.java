package com.karbyshev.zastaffka.view.mainView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.activities.PhotoDetailsActivity;
import com.karbyshev.zastaffka.adapter.MainAdapter;
import com.karbyshev.zastaffka.presenter.MainContract;
import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.network.receiver.NetworkChangeReceiver;
import com.karbyshev.zastaffka.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.karbyshev.zastaffka.common.Constants.ID;

public class MainActivityFragment extends Fragment
        implements RecyclerItemClickListener, MainContract.View {

    private static final String KEY_PAGE_NUMBER = "MainActivityFragment.KEY_PAGE_NUMBER";
    private static final String KEY_PAGES = "MainActivityFragment.KEY_PAGES";
    private static final String KEY_RECYCLER_STATE = "MainActivityFragment.KEY_RECYCLER_STATE";

    @BindView(R.id.mainRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.editTextToSearch)
    EditText mEditText;
    @BindView(R.id.buttonSearch)
    ImageView mImageView;
    @BindView(R.id.noInternetImageView)
    ImageView mNoConnectionImageView;
    @BindView(R.id.searchCardView)
    CardView mCardView;

    @Inject
    MainContract.Presenter mainPresenter;


    private LinearLayoutManager mLinearLayoutManager;
    private MainAdapter mMainAdapter;
    private int page = 1;
    private boolean isLoading = false;
    private Context context;
    private BroadcastReceiver mNetworkReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        mainPresenter.attachView(this);

        context = getActivity().getApplicationContext();
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMainAdapter = new MainAdapter();
        mMainAdapter.setOnItemClickListener(MainActivityFragment.this);
        mRecyclerView.setAdapter(mMainAdapter);

        mNetworkReceiver = new NetworkChangeReceiver();
        context.registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading
                        && isNetworkAvailable()
                        && (totalItemCount - visibleItemCount) <= (firstVisibleItem + 2)) {
                    page++;
                    mainPresenter.loadData(page);
                }
            }
        });

        if (savedInstanceState == null) {
            if (isNetworkAvailable()) {
                mainPresenter.viewIsReady();
                mainPresenter.loadData(page);
            } else {
                showNoConnectionStatement();
            }
        } else {
            page = savedInstanceState.getInt(KEY_PAGE_NUMBER);
            mMainAdapter.addAll(savedInstanceState.getParcelableArrayList(KEY_PAGES));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_PAGE_NUMBER, page);
        outState.putParcelableArrayList(KEY_PAGES, (ArrayList<? extends Parcelable>) mMainAdapter.getPhotoList());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public void onItemClick(int position, List<Photo> list) {
        Intent intent = new Intent(getActivity(), PhotoDetailsActivity.class);
        Photo photo = list.get(position);
        intent.putExtra(ID, photo.getId());

        startActivity(intent);
    }

    @OnClick(R.id.buttonSearch)
    public void search() {
        String editText = mEditText.getText().toString();

        if (TextUtils.isEmpty(editText)) {
            mMainAdapter.deleteAll();
            mainPresenter.loadData(page);
            mEditText.setError(getResources().getString(R.string.errorText));
        } else {
            mainPresenter.searchData(page, editText);
        }
    }

    @Override
    public void showNoConnectionStatement() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mCardView.setVisibility(View.INVISIBLE);
        mNoConnectionImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showIsConnectedStatement() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.VISIBLE);
        mNoConnectionImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void addAllToAdapter(List<Photo> list) {
        mMainAdapter.addAll(list);
    }

    @Override
    public void isLoading(boolean adapterIsLoading, boolean fragmentIsLoading) {
        mMainAdapter.setLoading(adapterIsLoading);
        isLoading = fragmentIsLoading;
    }

    @Override
    public void deleteAllFromAdapter() {
        mMainAdapter.deleteAll();
    }

    private boolean isNetworkAvailable() {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
