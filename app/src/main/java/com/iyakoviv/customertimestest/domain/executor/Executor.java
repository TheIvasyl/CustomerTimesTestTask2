package com.iyakoviv.customertimestest.domain.executor;

import com.iyakoviv.customertimestest.domain.interactors.base.AbstractInteractor;

public interface Executor {

    void execute(final AbstractInteractor interactor);
}
