package com.example.laboratory4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.laboratory4.Objetos.Geolocation;
import com.example.laboratory4.R;
import com.example.laboratory4.Services.OpenWeatherService;
import com.example.laboratory4.databinding.FragmentCityBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityFragment extends Fragment {

    FragmentCityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCityBinding.inflate(inflater, container, false);

        NavController navController = NavHostFragment.findNavController(CityFragment.this);
        Button goSearchWeatherButton = getActivity().findViewById(R.id.goSearchWeatherButton);
        goSearchWeatherButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_cityFragment_to_weatherFragment);
        });

        binding.searchLocationButton.setOnClickListener(v -> {
            String location = binding.locationInput.getText().toString();
            searchLocation(location);
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void searchLocation(String location){
        Log.d("msg-test", "location: "+location);
        OpenWeatherService openWeatherService = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherService.class);

        openWeatherService.getGeolocation(location, "792edf06f1f5ebcaf43632b55d8b03fe").enqueue(new Callback<List<Geolocation>>() {
            @Override
            public void onResponse(Call<List<Geolocation>> call, Response<List<Geolocation>> response) {
                if (response.isSuccessful()){
                    List<Geolocation> geolocations = response.body();
                    for(Geolocation geolocation: geolocations){
                        Log.d("msg-test", "Country: "+geolocation.country);
                        Log.d("msg-test", "Lon: "+geolocation.lon);
                        Log.d("msg-test", "Lat: "+geolocation.lat);
                        Log.d("msg-test", "---");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Geolocation>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}