package com.iyakoviv.customertimestest.data.datasource;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {


  private static Retrofit retrofit;

  static {
    retrofit = new Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
  }

  public static <T> T getService(Class<T> serviceClass) {
    return retrofit.create(serviceClass);
  }

}
