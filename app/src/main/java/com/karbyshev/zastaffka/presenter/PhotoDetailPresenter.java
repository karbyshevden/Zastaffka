package com.karbyshev.zastaffka.presenter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karbyshev.zastaffka.network.Request;
import com.karbyshev.zastaffka.view.IPhotoDetailsView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class PhotoDetailPresenter implements IPhotoDetailPresenter {
    private final static String CLIENT_KEY = "19d6afefc92f592eb3a28f4b3b69d309cca64f90d1a35033ae45bb22814dc533";
    private Disposable photoRequest = Disposables.empty();
    private final IPhotoDetailsView photoDetailsView;

    public PhotoDetailPresenter (IPhotoDetailsView photoDetailsView){
        this.photoDetailsView = photoDetailsView;
    }

    @Override
    public void loadLargePhoto(String id) {
        photoRequest = Request.getLargePhoto(id, CLIENT_KEY).subscribe(
                loadLargePhoto -> {
                    String largePhotoUrl = loadLargePhoto.getUrls().getRegular();
                    String username = loadLargePhoto.getUser().getUsername();
                    photoDetailsView.setImageView(largePhotoUrl);
                    photoDetailsView.setTextView(username);
                }, error -> {
                    photoDetailsView.errorLoadImage();
                }
        );
    }

    @Override
    public void setAsWallpaper(WallpaperManager wallpaperManager, Bitmap bitmap) {
        try {
            wallpaperManager.clear();
            wallpaperManager.setBitmap(bitmap);
            photoDetailsView.showToastSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
