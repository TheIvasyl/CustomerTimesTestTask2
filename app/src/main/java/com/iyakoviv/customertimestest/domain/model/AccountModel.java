package com.iyakoviv.customertimestest.domain.model;

import java.util.Map;

public class AccountModel {

    // since we can't have a specific model for Account
    // this map contains column name as a key
    // and it's value as a value
    private Map<String, String> mValue;

    public AccountModel(Map<String, String> value) {
        mValue = value;
    }

    public Map<String, String> getValue() {
        return mValue;
    }
}
