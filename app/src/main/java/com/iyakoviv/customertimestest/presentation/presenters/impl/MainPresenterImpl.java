package com.iyakoviv.customertimestest.presentation.presenters.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonElement;
import com.iyakoviv.customertimestest.domain.executor.Executor;
import com.iyakoviv.customertimestest.domain.executor.MainThread;
import com.iyakoviv.customertimestest.domain.interactors.DatabaseInteractor;
import com.iyakoviv.customertimestest.domain.interactors.DescribeNetworkInteractor;
import com.iyakoviv.customertimestest.domain.interactors.QueryNetworkInteractor;
import com.iyakoviv.customertimestest.domain.interactors.impl.DatabaseInteractorImpl;
import com.iyakoviv.customertimestest.domain.interactors.impl.DescribeNetworkInteractorImpl;
import com.iyakoviv.customertimestest.domain.interactors.impl.QueryNetworkInteractorImpl;
import com.iyakoviv.customertimestest.domain.model.AccountModel;
import com.iyakoviv.customertimestest.domain.repository.Repository;
import com.iyakoviv.customertimestest.presentation.presenters.MainPresenter;
import com.iyakoviv.customertimestest.presentation.presenters.base.AbstractPresenter;

import java.util.List;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        DescribeNetworkInteractor.Callback, QueryNetworkInteractor.Callback, DatabaseInteractor.Callback {

    private MainPresenter.View mView;
    private Repository mRepository;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view, Repository repository) {
        super(executor, mainThread);
        mView = view;
        mRepository = repository;
    }

    @Override
    public void setView(@NonNull MainPresenter.View mainActivity) {
      mView = mainActivity;
    }

    @Override
    public void loadDescribe() {
      if (!mRepository.isTableExists()) {
        DescribeNetworkInteractor interactor = new DescribeNetworkInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mRepository);
        Log.d("PRESENTER", "describe interactor created");
        interactor.execute();
      }
    }

    @Override
    public void loadQuery() {
      if (mRepository.isTableEmpty()) {
        QueryNetworkInteractor interactor = new QueryNetworkInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mRepository);
        Log.d("PRESENTER", "query interactor created");
        interactor.execute();
      }
    }


  @Override
  public void loadPageFromDb(int pageNumber) {
    DatabaseInteractor interactor = new DatabaseInteractorImpl(
            mExecutor,
            mMainThread,
            this,
            mRepository,
            pageNumber);
    Log.d("PRESENTER", "DB interactor created");
    interactor.execute();
  }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

  @Override
  public void onDescribeLoaded(Object object) {
    Log.d("PRESENTER", "DescribeGotLoaded");
    JsonElement element = (JsonElement) object;
    String text = element.getAsJsonObject().get("fields").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();

    //mView.setText(text);
  }

  @Override
  public void onDescribeLoadFailure() {
    Log.d("PRESENTER", "DesctibeDidntLoad");
  }

  @Override
  public void onDescribeLoadFailure(int code) {

  }

  @Override
  public void onQueryLoaded(Object object) {
    Log.d("PRESENTER", "QueryGorLoaded");
    JsonElement element = (JsonElement) object;
  }

  @Override
  public void onQueryLoadFailure() {

  }

  @Override
  public void onQueryLoadFailure(int code) {

  }

  @Override
  public void onPageLoaded(List<AccountModel> page) {
      Log.d("PRESENTER", "PAGE LOADED");

    mView.setLastLoadedPage(page);
  }

  @Override
  public void onPageLoadFailed() {

  }

  @Override
  public void onPageLoadFailed(int code) {

  }
}
