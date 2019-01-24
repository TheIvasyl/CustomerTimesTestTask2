package com.iyakoviv.customertimestest.presentation.presenters.impl;

import android.content.Intent;
import android.util.Log;

import com.iyakoviv.customertimestest.domain.executor.Executor;
import com.iyakoviv.customertimestest.domain.executor.MainThread;
import com.iyakoviv.customertimestest.domain.interactors.DescribeNetworkInteractor;
import com.iyakoviv.customertimestest.domain.interactors.QueryNetworkInteractor;
import com.iyakoviv.customertimestest.domain.interactors.impl.DescribeNetworkInteractorImpl;
import com.iyakoviv.customertimestest.domain.interactors.impl.QueryNetworkInteractorImpl;
import com.iyakoviv.customertimestest.domain.repository.Repository;
import com.iyakoviv.customertimestest.presentation.presenters.SplashPresenter;
import com.iyakoviv.customertimestest.presentation.presenters.base.AbstractPresenter;
import com.iyakoviv.customertimestest.presentation.ui.activities.MainActivity;

public class SplashPresenterImpl extends AbstractPresenter
        implements SplashPresenter, DescribeNetworkInteractor.Callback, QueryNetworkInteractor.Callback {

  SplashPresenter.View mView;
  Repository mRepository;

  public SplashPresenterImpl(Executor executor, MainThread mainThread, View view, Repository repository) {
    super(executor, mainThread);
    mView = view;
    mRepository = repository;
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
    else {
      mView.goToMainActivity();
      Log.d("PRESENTER", "table is filled");
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
    else {
      mView.goToMainActivity();
      Log.d("PRESENTER", "table is filled");
    }
  }


  @Override
  public void onDescribeLoaded(Object object) {
    loadQuery();
  }

  @Override
  public void onDescribeLoadFailure() {
  }

  @Override
  public void onDescribeLoadFailure(int code) {

  }

  @Override
  public void onQueryLoaded(Object object) {
    Log.d("PRESENTER", "QUERRY LOADED");
    mView.goToMainActivity();
  }

  @Override
  public void onQueryLoadFailure() {

  }

  @Override
  public void onQueryLoadFailure(int code) {

  }
}
