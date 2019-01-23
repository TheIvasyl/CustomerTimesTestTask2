package com.iyakoviv.customertimestest.domain.model;

import java.util.Map;

public class AccountModel {

    private Map<String, String> mValue;

    public AccountModel(Map<String, String> value) {
        mValue = value;
    }

    public Map<String, String> getValue() {
        return mValue;
    }
}
