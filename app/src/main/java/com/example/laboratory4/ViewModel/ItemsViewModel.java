package com.example.laboratory4.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laboratory4.Objetos.Geolocation;
import com.example.laboratory4.Objetos.Weather;

import java.util.List;

public class ItemsViewModel extends ViewModel {
    private final MutableLiveData<List<Geolocation>> geolocations = new MutableLiveData<>();
    private final MutableLiveData<List<Weather>> weathers = new MutableLiveData<>();

    public MutableLiveData<List<Geolocation>> getGeolocations() {
        return geolocations;
    }

    public MutableLiveData<List<Weather>> getWeathers() {
        return weathers;
    }
}
