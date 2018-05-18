package com.karbyshev.zastaffka.presenter;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import com.karbyshev.zastaffka.base.PresenterBase;
import com.karbyshev.zastaffka.network.Request;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

import static com.karbyshev.zastaffka.common.Constants.CLIENT_KEY;

public class PhotoDetailPresenter
        extends PresenterBase<PhotoDetailsContract.View>
        implements PhotoDetailsContract.Presenter{
    private Disposable photoRequest = Disposables.empty();

    @Override
    public void loadLargePhoto(String id) {
        getView().showProgressIndicator();
        photoRequest = Request.getLargePhoto(id, CLIENT_KEY).subscribe(
                loadLargePhoto -> {
                    String largePhotoUrl = loadLargePhoto.getUrls().getRegular();
                    String username = loadLargePhoto.getUser().getUsername();
                    getView().setImageView(largePhotoUrl);
                    getView().setTextView(username);
                }, error -> {
                    getView().errorLoadImage();
                }
        );
    }


    @Override
    public void downloadPhoto() {
        getView().saveImageIntoGallery();
    }

    @Override
    public void setAsWallpaper(WallpaperManager wallpaperManager, Bitmap bitmap) {
        try {
            wallpaperManager.clear();
            wallpaperManager.setBitmap(bitmap);
            getView().showToastSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void viewIsReady() {
    }
}
