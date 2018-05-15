package com.karbyshev.zastaffka.view;

public interface IPhotoDetailsView {

    void showToastSuccessful();

    void errorLoadImage();

    void setImageView(String photoUrl);

    void setTextView(String username);

    void showProgressBar();

    void hideProgressBar();
}
