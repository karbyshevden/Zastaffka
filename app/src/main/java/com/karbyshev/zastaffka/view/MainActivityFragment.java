package com.karbyshev.zastaffka.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.adapter.MainAdapter;
import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.presenter.IMainPresenter;
import com.karbyshev.zastaffka.presenter.MainPresenter;
import com.karbyshev.zastaffka.presenter.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityFragment extends Fragment implements RecyclerItemClickListener, IMainView{
    public static boolean isLoading = false;

    @BindView(R.id.mainRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.editTextToSearch)
    EditText mEditText;
    @BindView(R.id.buttonSearch)
    ImageView mImageView;

    private LinearLayoutManager mLinearLayoutManager;
    private MainAdapter mMainAdapter;
    private IMainPresenter mainPresenter;
    private int page = 1;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        context = getActivity().getApplicationContext();
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMainAdapter = new MainAdapter();
        mMainAdapter.setOnItemClickListener(MainActivityFragment.this);
        mRecyclerView.setAdapter(mMainAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading
                        && networkInfo.isConnected()
                        && (totalItemCount - visibleItemCount) <= (firstVisibleItem + 2)) {
                    page++;
                    mainPresenter.loadData(mMainAdapter, page);
                }
            }
        });

        if (networkInfo != null && networkInfo.isConnected()){
            if (mainPresenter == null) mainPresenter = new MainPresenter(this);

            mainPresenter.loadData(mMainAdapter, page);
        } else {
            showNoConnectionMessage();
        }


        return view;
    }

    @Override
    public void onItemClick(int position, List<Photo> list) {
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonSearch)
    public void search() {
        String editText = mEditText.getText().toString();

        if (TextUtils.isEmpty(editText)) {
            mMainAdapter.deleteAll();
            mainPresenter.loadData(mMainAdapter, page);
            mEditText.setError(getResources().getString(R.string.errorText));
        } else {
            mainPresenter.searchData(mMainAdapter, page, editText);
        }
    }

    @Override
    public void showNoConnectionMessage() {
        Toast.makeText(context, "No Intenet Connection", Toast.LENGTH_SHORT)
                .show();
    }
}
