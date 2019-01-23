package com.iyakoviv.customertimestest.presentation.ui;


public interface BaseView {

    void showProgress();

    void hideProgress();

    void showError(String message);
}
