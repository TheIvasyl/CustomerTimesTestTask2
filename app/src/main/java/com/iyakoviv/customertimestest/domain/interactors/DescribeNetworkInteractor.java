package com.iyakoviv.customertimestest.domain.interactors;


import com.iyakoviv.customertimestest.domain.interactors.base.Interactor;


public interface DescribeNetworkInteractor extends Interactor {

    interface Callback {
        public void onDescribeLoaded(Object object);
        public void onDescribeLoadFailure();
        public void onDescribeLoadFailure(int code);
    }

}
