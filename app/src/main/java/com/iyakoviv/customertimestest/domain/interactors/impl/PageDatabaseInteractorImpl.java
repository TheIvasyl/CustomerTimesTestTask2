package com.iyakoviv.customertimestest.domain.interactors.impl;

import android.util.Log;

import com.iyakoviv.customertimestest.domain.executor.Executor;
import com.iyakoviv.customertimestest.domain.executor.MainThread;
import com.iyakoviv.customertimestest.domain.interactors.PageDatabaseInteractor;
import com.iyakoviv.customertimestest.domain.interactors.base.AbstractInteractor;
import com.iyakoviv.customertimestest.domain.model.AccountModel;
import com.iyakoviv.customertimestest.domain.repository.Repository;

import java.util.List;

public class PageDatabaseInteractorImpl extends AbstractInteractor implements PageDatabaseInteractor {


  private PageDatabaseInteractor.Callback mCallback;
  private Repository mRepository;
  private int pageNumber;
  private final int mLimit = 40;

  public PageDatabaseInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    Callback callback, Repository repository, int pageNumber) {
    super(threadExecutor, mainThread);
    mCallback = callback;
    mRepository = repository;
    this.pageNumber = pageNumber;
  }

  @Override
  public void run() {
    Log.d("INERACTOR", "INTERACTOR RAN");
    final List<AccountModel> page = mRepository.getPage(pageNumber, mLimit);

    mMainThread.post(new Runnable() {
      @Override
      public void run() {
        mCallback.onPageLoaded(page, pageNumber);
      }
    });
  }
}
