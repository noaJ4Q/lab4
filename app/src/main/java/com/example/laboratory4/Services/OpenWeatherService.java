package com.example.laboratory4.Services;

import com.example.laboratory4.Objetos.Geolocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {
    @GET("/geo/1.0/direct")
    Call<List<Geolocation>> getGeolocation(@Query("q") String city,
                              @Query("appid") String apiKey);
}
