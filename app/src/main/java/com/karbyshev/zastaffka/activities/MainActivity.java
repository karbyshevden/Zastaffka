package com.karbyshev.zastaffka.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.view.mainView.MainActivityFragment;
import com.karbyshev.zastaffka.view.mainView.dagger.DaggerMainComponent;
import com.karbyshev.zastaffka.view.mainView.dagger.MainComponent;
import com.karbyshev.zastaffka.view.mainView.dagger.MainModule;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String FRAGMENT_TAG = "MainActivity.MainFragment";

    @BindView(R.id.main_fragment_container)
    FrameLayout fragmentContainer;

    private MainActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (getSupportFragmentManager().getFragments() == null
                || getSupportFragmentManager().getFragments().isEmpty()) {
            fragment = new MainActivityFragment();
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.main_fragment_container, fragment, FRAGMENT_TAG
            ).commit();
        } else {
            fragment = (MainActivityFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG);
        }

        createMainComponent().inject(fragment);
    }

    private MainComponent createMainComponent() {
        return DaggerMainComponent.builder()
                .mainModule(new MainModule())
                .build();
    }
}

