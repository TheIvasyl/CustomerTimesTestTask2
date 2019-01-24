package com.iyakoviv.customertimestest.domain.interactors.impl;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.iyakoviv.customertimestest.data.datasource.services.QueryNetworkService;
import com.iyakoviv.customertimestest.domain.executor.Executor;
import com.iyakoviv.customertimestest.domain.executor.MainThread;
import com.iyakoviv.customertimestest.domain.interactors.QueryNetworkInteractor;
import com.iyakoviv.customertimestest.domain.interactors.base.AbstractInteractor;
import com.iyakoviv.customertimestest.domain.repository.Repository;

public class QueryNetworkInteractorImpl extends AbstractInteractor implements QueryNetworkInteractor {

  private QueryNetworkInteractor.Callback mCallback;
  private Repository mRepository;

  public QueryNetworkInteractorImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    Callback callback, Repository repository) {
    super(threadExecutor, mainThread);
    mCallback = callback;
    mRepository = repository;
  }

  @Override
  public void run() {
   Log.d("INTERACTOR", "interactor ran");
   QueryNetworkService.getQuery(new Callback() {
     @Override
     public void onQueryLoaded(Object object) {
       Log.d("INTERACTOR", "DATA LOADED");
       final JsonElement query = (JsonElement) object;
       final JsonArray recordsJson = query.getAsJsonObject().get("records").getAsJsonArray();
       mRepository.insertAll(recordsJson);
       mMainThread.post(new Runnable() {
         @Override
         public void run() {
           mCallback.onQueryLoaded(recordsJson);
         }
       });
     }

     @Override
     public void onQueryLoadFailure() {

     }

     @Override
     public void onQueryLoadFailure(int code) {

     }
   });
  }
}
