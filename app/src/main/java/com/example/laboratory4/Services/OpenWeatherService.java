package com.example.laboratory4.Services;

import com.example.laboratory4.Objetos.Geolocation;
import com.example.laboratory4.Objetos.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {
    @GET("/geo/1.0/direct")
    Call<List<Geolocation>> getGeolocation(@Query("q") String city,
                              @Query("appid") String apiKey);

    @GET("/data/2.5/weather")
    Call<Weather> getWeather(@Query("lat") double lat,
                             @Query("lon") double lon,
                             @Query("appid") String apiKey);
}
