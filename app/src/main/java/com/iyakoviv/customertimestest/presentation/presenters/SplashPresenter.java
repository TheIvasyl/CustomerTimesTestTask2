package com.iyakoviv.customertimestest.presentation.presenters;

import com.iyakoviv.customertimestest.domain.model.AccountModel;
import com.iyakoviv.customertimestest.presentation.ui.BaseView;

import java.util.List;

public interface SplashPresenter {

  interface View extends BaseView {
    void goToMainActivity();
  }

  void loadDescribe();

  void loadQuery();

}
