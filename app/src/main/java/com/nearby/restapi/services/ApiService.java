package com.nearby.restapi.services;

import com.nearby.restapi.model.Model;


import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("textsearch/json?")
    Call<Model> getPost(@QueryMap(encoded = true) LinkedHashMap<String, String> params);
}
