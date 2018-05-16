package com.karbyshev.zastaffka.base;

public abstract class PresenterBase<T extends MvpView> implements MvpPresenter<T> {
    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void destroy() {

    }

    protected boolean isViewAttached() {
        return view != null;
    }

    public T getView() {
        return view;
    }
}
