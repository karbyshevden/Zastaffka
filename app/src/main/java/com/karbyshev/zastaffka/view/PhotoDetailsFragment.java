package com.karbyshev.zastaffka.view;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import com.karbyshev.zastaffka.R;
import com.karbyshev.zastaffka.presenter.IPhotoDetailPresenter;
import com.karbyshev.zastaffka.presenter.PhotoDetailPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.karbyshev.zastaffka.view.MainActivityFragment.ID;

public class PhotoDetailsFragment extends Fragment implements IPhotoDetailsView{
    @BindView(R.id.photoDetailImageView)
    ImageView mImageView;
    @BindView(R.id.photoDetailAuthorTextView)
    TextView mTextView;
    @BindView(R.id.photoDetailButton)
    Button mButton;
    @BindView(R.id.photoDetailProgressBar)
    ProgressBar mProgressBar;

    private IPhotoDetailPresenter mPhotoDetailPresenter;
    private Context context;
    private WallpaperManager mWallpaperManager;
    private Bitmap mBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_details, container, false);
        ButterKnife.bind(this, view);
        context = getActivity().getApplicationContext();

        mPhotoDetailPresenter = new PhotoDetailPresenter(this);
        mWallpaperManager = WallpaperManager.getInstance(context);
        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra(ID);

        mPhotoDetailPresenter.loadLargePhoto(id);

        return view;
    }

    @OnClick(R.id.photoDetailButton)
    public void setAsWallpaper(){
        mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
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
        Picasso.get().load(photoUrl).into(mImageView);
    }

    @Override
    public void setTextView(String username) {
        mTextView.setText(username);
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
