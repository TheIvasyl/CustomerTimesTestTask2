package com.iyakoviv.customertimestest.domain.executor;

public interface MainThread {

    void post(final Runnable runnable);
}
