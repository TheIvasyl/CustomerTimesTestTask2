package com.iyakoviv.customertimestest.data.datasource.services;

import com.google.gson.JsonElement;
import com.iyakoviv.customertimestest.data.datasource.RestClient;
import com.iyakoviv.customertimestest.domain.interactors.QueryNetworkInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryNetworkService {

  public static void getQuery(final QueryNetworkInteractor.Callback callback) {

    QueryService describeService = RestClient.getService(QueryService.class);

    Call<JsonElement> call = describeService.getQuery();
    call.enqueue(new Callback<JsonElement>() {
      @Override
      public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        JsonElement queryJson = response.body();
        if (queryJson != null) {
          callback.onQueryLoaded(queryJson);
        } else {
          callback.onQueryLoadFailure(response.code());
        }
      }

      @Override
      public void onFailure(Call<JsonElement> call, Throwable t) {
        t.printStackTrace();
        callback.onQueryLoadFailure();
      }
    });
  }
}
