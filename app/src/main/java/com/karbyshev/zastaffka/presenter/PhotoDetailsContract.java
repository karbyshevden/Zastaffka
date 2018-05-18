package com.karbyshev.zastaffka.presenter;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import com.karbyshev.zastaffka.base.MvpPresenter;
import com.karbyshev.zastaffka.base.MvpView;

public interface PhotoDetailsContract {

    interface View extends MvpView {

        void showToastSuccessful();

        void errorLoadImage();

        void setImageView(String photoUrl);

        void setTextView(String username);

        void showProgressIndicator();

        void saveImageIntoGallery();
    }

    interface Presenter extends MvpPresenter<View>{

        void loadLargePhoto(String id);

        void setAsWallpaper(WallpaperManager wallpaperManager, Bitmap bitmap);

        void downloadPhoto();
    }
}
