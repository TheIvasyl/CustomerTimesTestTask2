package com.iyakoviv.customertimestest.presentation.presenters;

import com.iyakoviv.customertimestest.presentation.presenters.base.BasePresenter;
import com.iyakoviv.customertimestest.presentation.ui.BaseView;


public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        void setText(String text);
    }

    void loadDescribe();

    void loadQuery();

    void loadPageFromDb(int offset);

}
