package com.iyakoviv.customertimestest.data.datasource.services;

import com.google.gson.JsonElement;
import com.iyakoviv.customertimestest.data.datasource.NetworkConstants;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DescribeService {

  @GET(NetworkConstants.API_URL + NetworkConstants.DESCRIBE_URL)
  Call<JsonElement> getDescribe();
}
