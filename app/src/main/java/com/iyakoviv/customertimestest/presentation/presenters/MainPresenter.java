package com.iyakoviv.customertimestest.presentation.presenters;

import com.iyakoviv.customertimestest.domain.model.AccountModel;
import com.iyakoviv.customertimestest.presentation.presenters.base.BasePresenter;
import com.iyakoviv.customertimestest.presentation.ui.BaseView;

import java.util.List;

import io.reactivex.processors.PublishProcessor;


public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        void setText(String text);
        void addNewItems(List<AccountModel> page);
    }

    void setView(View view);

    void loadPageFromDb(int pageNumber);
}
