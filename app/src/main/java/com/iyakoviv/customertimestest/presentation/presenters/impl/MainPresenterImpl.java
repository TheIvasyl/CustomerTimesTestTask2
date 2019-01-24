package com.iyakoviv.customertimestest.presentation.presenters.impl;

import android.util.Log;

import com.iyakoviv.customertimestest.domain.executor.Executor;
import com.iyakoviv.customertimestest.domain.executor.MainThread;
import com.iyakoviv.customertimestest.domain.interactors.PageDatabaseInteractor;
import com.iyakoviv.customertimestest.domain.interactors.impl.PageDatabaseInteractorImpl;
import com.iyakoviv.customertimestest.domain.model.AccountModel;
import com.iyakoviv.customertimestest.domain.repository.Repository;
import com.iyakoviv.customertimestest.presentation.presenters.MainPresenter;
import com.iyakoviv.customertimestest.presentation.presenters.base.AbstractPresenter;

import java.util.List;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter, PageDatabaseInteractor.Callback {

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
    public void setView(MainPresenter.View mainActivity) {
      mView = mainActivity;
    }




  @Override
  public void loadPageFromDb(int pageNumber) {
    PageDatabaseInteractor interactor = new PageDatabaseInteractorImpl(
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
  public void onPageLoaded(List<AccountModel> page, int pageNumber) {
      Log.d("PRESENTER", "PAGE LOADED");

    mView.addNewItems(page);
  }

  @Override
  public void onPageLoadFailed() {

  }

  @Override
  public void onPageLoadFailed(int code) {

  }
}
