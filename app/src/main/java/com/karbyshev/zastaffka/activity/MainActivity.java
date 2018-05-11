package com.karbyshev.zastaffka.activity;

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
import com.karbyshev.zastaffka.presenter.IMainPresenter;
import com.karbyshev.zastaffka.presenter.MainPresenter;
import com.karbyshev.zastaffka.presenter.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

