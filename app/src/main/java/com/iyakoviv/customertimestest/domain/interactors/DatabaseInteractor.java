package com.iyakoviv.customertimestest.domain.interactors;

import com.iyakoviv.customertimestest.domain.interactors.base.Interactor;
import com.iyakoviv.customertimestest.domain.model.AccountModel;

import java.util.List;

public interface DatabaseInteractor extends Interactor {

  interface Callback {
    void onPageLoaded(List<AccountModel> page);
    void onPageLoadFailed();
    void onPageLoadFailed(int code);
  }
}
