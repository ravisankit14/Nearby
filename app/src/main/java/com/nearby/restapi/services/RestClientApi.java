package com.nearby.restapi.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientApi {

    private static String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static Retrofit mRetrofit;

    public static Retrofit getClient(){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
