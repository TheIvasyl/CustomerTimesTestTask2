package com.iyakoviv.customertimestest.domain.repository;

import com.google.gson.JsonArray;
import com.iyakoviv.customertimestest.domain.model.AccountModel;

import java.util.List;

public interface Repository {

    void createTable(JsonArray fields);

    void insertAll(JsonArray records);

    List<AccountModel> getPage(int offset, int limit);

    boolean isTableExists();

    boolean isTableEmpty();
}
