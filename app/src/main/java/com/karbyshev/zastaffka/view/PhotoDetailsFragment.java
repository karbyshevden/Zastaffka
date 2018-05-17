package com.karbyshev.zastaffka.view;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.presenter.PhotoDetailPresenter;
import com.karbyshev.zastaffka.presenter.PhotoDetailsContract;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.karbyshev.zastaffka.common.Constants.ID;

public class PhotoDetailsFragment extends Fragment implements PhotoDetailsContract.View{
    @BindView(R.id.photoDetailImageView)
    BigImageView mImageView;
    @BindView(R.id.photoDetailAuthorTextView)
    TextView mTextView;
    @BindView(R.id.photoDetailButton)
    Button mButton;
    @BindView(R.id.photoDetailProgressBar)
    ProgressBar mProgressBar;

    private PhotoDetailsContract.Presenter mPhotoDetailPresenter;
    private Context context;
    private WallpaperManager mWallpaperManager;
    private Bitmap mBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        BigImageViewer.initialize(GlideImageLoader.with(context));
        View view = inflater.inflate(R.layout.fragment_photo_details, container, false);
        ButterKnife.bind(this, view);

        mPhotoDetailPresenter = new PhotoDetailPresenter();
        mPhotoDetailPresenter.attachView(this);
        mWallpaperManager = WallpaperManager.getInstance(context);

        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra(ID);

        mPhotoDetailPresenter.loadLargePhoto(id);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotoDetailPresenter.detachView();
    }

    @OnClick(R.id.photoDetailButton)
    public void setAsWallpaper(){
        File path = mImageView.getCurrentImageFile();
        mBitmap = BitmapFactory.decodeFile(path.getAbsolutePath());
        mPhotoDetailPresenter.setAsWallpaper(mWallpaperManager, mBitmap);
    }

    @Override
    public void showToastSuccessful() {
        Toast.makeText(context, "Wallpaper set!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorLoadImage() {
        Toast.makeText(context, "Oops!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageView(String photoUrl) {
        mImageView.showImage(Uri.parse(photoUrl));
    }

    @Override
    public void setTextView(String username) {
        mTextView.setText(username);
    }

    @Override
    public void showProgressIndicator() {
        mImageView.setProgressIndicator(new ProgressPieIndicator());
    }


    @Override
    public void showProgressBar() {
        mButton.setVisibility(View.INVISIBLE);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mButton.setVisibility(View.VISIBLE);
    }
}
