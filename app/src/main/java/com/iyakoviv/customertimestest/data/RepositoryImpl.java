package com.iyakoviv.customertimestest.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.iyakoviv.customertimestest.data.database.AccountsDatabaseHelper;
import com.iyakoviv.customertimestest.domain.model.AccountModel;
import com.iyakoviv.customertimestest.domain.repository.Repository;

import java.util.List;

public class RepositoryImpl implements Repository {

  private Context mContext;

  public RepositoryImpl(Context context) {
    mContext = context;
  }

  @Override
  public void createTable(JsonArray fields) {
    AccountsDatabaseHelper databaseHelper = AccountsDatabaseHelper.getInstance(mContext);
    databaseHelper.createTable(fields);
    Log.d("REPOSITORY", "TABLE CREATED");
  }

  @Override
  public void insertAll(JsonArray records) {
    AccountsDatabaseHelper databaseHelper = AccountsDatabaseHelper.getInstance(mContext);
    databaseHelper.insertAll(records);
    Log.d("REPOSITORY", "TABLE FILLED");
  }

  @Override
  public List<AccountModel> getPage(int pageNumber, int limit) {
    AccountsDatabaseHelper databaseHelper = AccountsDatabaseHelper.getInstance(mContext);
    List<AccountModel> page = databaseHelper.getPage(pageNumber, limit);
    Log.d("REPOSITORY", "PAGE LOADED");
    return page;
  }

  @Override
  public boolean isTableExists() {
    AccountsDatabaseHelper databaseHelper = AccountsDatabaseHelper.getInstance(mContext);
    return databaseHelper.isTableExists();
  }

  @Override
  public boolean isTableEmpty() {
    AccountsDatabaseHelper databaseHelper = AccountsDatabaseHelper.getInstance(mContext);
    return databaseHelper.isTableEmpty();
  }


}
