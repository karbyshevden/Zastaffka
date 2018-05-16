package com.karbyshev.zastaffka.presenter;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import com.karbyshev.zastaffka.base.PresenterBase;
import com.karbyshev.zastaffka.network.Request;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class PhotoDetailPresenter
        extends PresenterBase<PhotoDetailsContract.View>
        implements PhotoDetailsContract.Presenter{
    private final static String CLIENT_KEY = "19d6afefc92f592eb3a28f4b3b69d309cca64f90d1a35033ae45bb22814dc533";
    private Disposable photoRequest = Disposables.empty();

    @Override
    public void loadLargePhoto(String id) {
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
