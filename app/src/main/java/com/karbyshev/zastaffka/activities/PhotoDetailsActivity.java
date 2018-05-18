package com.karbyshev.zastaffka.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.view.photoDetailsView.PhotoDetailsFragment;
import com.karbyshev.zastaffka.view.photoDetailsView.dagger.DaggerPhotoDetailsComponent;
import com.karbyshev.zastaffka.view.photoDetailsView.dagger.PhotoDetailsComponent;
import com.karbyshev.zastaffka.view.photoDetailsView.dagger.PhotoDetailsModule;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailsActivity extends AppCompatActivity {
    private static final String FRAGMENT_TAG = "PhotoDetailsActivity.MainFragment";

    @BindView(R.id.photo_fragment_container)
    FrameLayout fragmentContainer;

    private PhotoDetailsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        ButterKnife.bind(this);

        if (getSupportFragmentManager().getFragments() == null
                || getSupportFragmentManager().getFragments().isEmpty()) {
            fragment = new PhotoDetailsFragment();
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.photo_fragment_container, fragment, FRAGMENT_TAG
            ).commit();
        } else {
            fragment = (PhotoDetailsFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG);
        }
        createPhotoDetailsComponent().inject(fragment);
    }

    private PhotoDetailsComponent createPhotoDetailsComponent() {
        return DaggerPhotoDetailsComponent.builder()
                .photoDetailsModule(new PhotoDetailsModule())
                .build();
    }

}
