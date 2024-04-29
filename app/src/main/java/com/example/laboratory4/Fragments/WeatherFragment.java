package com.example.laboratory4.Fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.laboratory4.AppActivity;
import com.example.laboratory4.Objetos.Weather;
import com.example.laboratory4.R;
import com.example.laboratory4.Recycler.WeatherAdapter;
import com.example.laboratory4.Services.OpenWeatherService;
import com.example.laboratory4.databinding.FragmentWeatherBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment implements SensorEventListener{
    FragmentWeatherBinding binding;
    AppActivity appActivity;
    WeatherAdapter weatherAdapter = new WeatherAdapter();
    List<Weather> weathers;
    SensorManager sensorManager;
    float xMageneticField;
    float yMagneticField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appActivity = (AppActivity)getActivity();
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        NavController navController = NavHostFragment.findNavController(WeatherFragment.this);
        Button goSearchLocationButton = getActivity().findViewById(R.id.goSearchLocationButton);
        goSearchLocationButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_weatherFragment_to_cityFragment);
        });

        initializeRecycler();

        binding.searchWeatherButton.setOnClickListener(v -> {
            String lat = binding.latInput.getText().toString();
            String lon = binding.lonInput.getText().toString();
            searchWeather(Float.parseFloat(lat), Float.parseFloat(lon));
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    private void searchWeather(float lat, float lon){
        binding.searchWeatherButton.setEnabled(false);
        OpenWeatherService openWeatherService = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherService.class);

        openWeatherService.getWeather(lat, lon, "792edf06f1f5ebcaf43632b55d8b03fe").enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
               binding.searchWeatherButton.setEnabled(true);
               if (response.isSuccessful()){
                   Weather weather = response.body();

                   weather.setWind(calculateWindDirection());
                   weathers.add(weather);
                   appActivity.setWeathers(weathers);
                   weatherAdapter.notifyItemInserted(weathers.size());
               }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable throwable) {
                binding.searchWeatherButton.setEnabled(true);
            }
        });
    }

    private void initializeRecycler(){
        weathers = appActivity.getWeathers();

        weatherAdapter.setContext(getContext());
        weatherAdapter.setWeathers(weathers);

        binding.weatherRecycler.setAdapter(weatherAdapter);
        binding.weatherRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        if (sensorType == Sensor.TYPE_MAGNETIC_FIELD){
            Log.d("msg-test", Arrays.toString(event.values));
            xMageneticField = event.values[0];
            yMagneticField = event.values[1];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private String calculateWindDirection(){
        return "ga";
    }
}