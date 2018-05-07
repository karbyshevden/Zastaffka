package com.karbyshev.zastaffka.view;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.adapter.MainAdapter;
import com.karbyshev.zastaffka.models.Photo;
import com.karbyshev.zastaffka.presenter.MainPresenter;
import com.karbyshev.zastaffka.presenter.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener {

    @BindView(R.id.mainRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.editTextToSearch)
    EditText mEditText;
    @BindView(R.id.buttonSearch)
    ImageView mImageView;

    private LinearLayoutManager mLinearLayoutManager;
    private MainAdapter mMainAdapter;
    private IMainActivity mIMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMainAdapter = new MainAdapter();
        mMainAdapter.setOnItemClickListiner(MainActivity.this);
        mRecyclerView.setAdapter(mMainAdapter);
        mIMainActivity = new MainPresenter();

        mIMainActivity.startLoad(mMainAdapter);
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

