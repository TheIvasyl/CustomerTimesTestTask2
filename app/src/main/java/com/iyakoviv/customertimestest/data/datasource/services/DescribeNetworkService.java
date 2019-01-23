package com.iyakoviv.customertimestest.data.datasource.services;

import com.google.gson.JsonElement;
import com.iyakoviv.customertimestest.data.datasource.RestClient;
import com.iyakoviv.customertimestest.domain.interactors.DescribeNetworkInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescribeNetworkService {

  public static void getDescribe(final DescribeNetworkInteractor.Callback callback){

    DescribeService describeService = RestClient.getService(DescribeService.class);

    Call<JsonElement> call = describeService.getDescribe();
    call.enqueue(new Callback<JsonElement>() {
      @Override
      public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        JsonElement element = response.body();
        if(element != null) {
          callback.onDescribeLoaded(element);
        }
        else {
          callback.onDescribeLoadFailure(response.code());
        }
      }

      @Override
      public void onFailure(Call<JsonElement> call, Throwable t) {
        t.printStackTrace();
        callback.onDescribeLoadFailure();
      }
    });
  }
}
