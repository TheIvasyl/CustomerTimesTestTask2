package com.iyakoviv.customertimestest.domain.interactors.impl;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.iyakoviv.customertimestest.data.datasource.services.DescribeNetworkService;
import com.iyakoviv.customertimestest.domain.executor.Executor;
import com.iyakoviv.customertimestest.domain.executor.MainThread;
import com.iyakoviv.customertimestest.domain.interactors.DescribeNetworkInteractor;
import com.iyakoviv.customertimestest.domain.interactors.base.AbstractInteractor;
import com.iyakoviv.customertimestest.domain.repository.Repository;

import org.json.JSONArray;


public class DescribeNetworkInteractorImpl extends AbstractInteractor implements DescribeNetworkInteractor {

    private DescribeNetworkInteractor.Callback mCallback;
    private Repository                mRepository;

    public DescribeNetworkInteractorImpl(Executor threadExecutor,
                                         MainThread mainThread,
                                         Callback callback, Repository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {
      Log.d("INTERACTOR", "interactor ran");
      DescribeNetworkService.getDescribe(new Callback() {
        @Override
        public void onDescribeLoaded(Object object) {
          Log.d("INTERACTOR", "DATA LOADED");
          final JsonElement describe = (JsonElement) object;
          final JsonArray fields = describe.getAsJsonObject().get("fields").getAsJsonArray();
          mMainThread.post(new Runnable() {
            @Override
            public void run() {
              mRepository.createTable(describe.getAsJsonObject().get("fields").getAsJsonArray());
              mCallback.onDescribeLoaded(fields);
            }
          });
        }

        @Override
        public void onDescribeLoadFailure() {
          Log.d("INTERACTOR", "ON FAIL");
        }

        @Override
        public void onDescribeLoadFailure(int code) {
          Log.d("INTERACTOR", "ON FAIL" + code);
        }
      });
    }
}
