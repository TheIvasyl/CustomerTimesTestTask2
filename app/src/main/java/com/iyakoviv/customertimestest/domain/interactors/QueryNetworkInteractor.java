package com.iyakoviv.customertimestest.domain.interactors;

import com.google.gson.JsonArray;
import com.iyakoviv.customertimestest.domain.interactors.base.Interactor;

public interface QueryNetworkInteractor extends Interactor {

  interface Callback {
    public void onQueryLoaded(Object object);
    public void onQueryLoadFailure();
    public void onQueryLoadFailure(int code);
  }

}
