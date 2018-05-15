package com.karbyshev.zastaffka.presenter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public interface IPhotoDetailPresenter {

    void loadLargePhoto(String id);

    void setAsWallpaper(WallpaperManager wallpaperManager, Bitmap bitmap);
}
