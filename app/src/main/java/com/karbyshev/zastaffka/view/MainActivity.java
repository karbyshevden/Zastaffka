package com.karbyshev.zastaffka.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.adapter.MainAdapter;
import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.network.Request;
import com.karbyshev.zastaffka.presenter.IMainPresenter;
import com.karbyshev.zastaffka.presenter.MainPresenter;
import com.karbyshev.zastaffka.presenter.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMainAdapter = new MainAdapter();
        mMainAdapter.setOnItemClickListener(MainActivity.this);
        mRecyclerView.setAdapter(mMainAdapter);
        mainPresenter = new MainPresenter();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + 2)) {
                    page++;
                    mainPresenter.loadData(mMainAdapter, page);
                    System.out.println(page);
                }
            }
        });

        mainPresenter.loadData(mMainAdapter, page);
    }


    @Override
    public void onItemClick(int position, List<Photo> list) {
        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonSearch)
    public void search() {
        String editText = mEditText.getText().toString();

        if (TextUtils.isEmpty(editText)) {
            mEditText.setError(getResources().getString(R.string.errorText));
        } else {
            Toast.makeText(this, editText, Toast.LENGTH_SHORT).show();
        }
    }
}

