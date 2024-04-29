package com.example.laboratory4.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.laboratory4.AppActivity;
import com.example.laboratory4.Objetos.Geolocation;
import com.example.laboratory4.R;
import com.example.laboratory4.Recycler.GeolocationAdapter;
import com.example.laboratory4.Services.OpenWeatherService;
import com.example.laboratory4.ViewModel.ItemsViewModel;
import com.example.laboratory4.databinding.FragmentCityBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityFragment extends Fragment implements SensorEventListener {

    FragmentCityBinding binding;

    GeolocationAdapter geolocationAdapter = new GeolocationAdapter();
    SensorManager sensorManager;
    ItemsViewModel itemsViewModel;
    List<Geolocation> geolocationsFragment = new ArrayList<>();
    boolean dialogOpened = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCityBinding.inflate(inflater, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        itemsViewModel = new ViewModelProvider(requireActivity()).get(ItemsViewModel.class);

        NavController navController = NavHostFragment.findNavController(CityFragment.this);
        Button goSearchWeatherButton = getActivity().findViewById(R.id.goSearchWeatherButton);
        goSearchWeatherButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_cityFragment_to_weatherFragment);
        });

        itemsViewModel.getGeolocations().observe(getActivity(), geolocations -> {
            geolocationsFragment = geolocations;
        });

        initializeRecycler();

        binding.searchLocationButton.setOnClickListener(v -> {
            String location = binding.locationInput.getText().toString();
            searchLocation(location);
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    private void initializeRecycler(){
        geolocationAdapter.setContext(getContext());
        geolocationAdapter.setGeolocations(geolocationsFragment);

        binding.locationRecycler.setAdapter(geolocationAdapter);
        binding.locationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void searchLocation(String location){
        binding.searchLocationButton.setEnabled(false);
        Log.d("msg-test", "loc: "+location);
        OpenWeatherService openWeatherService = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherService.class);

        openWeatherService.getGeolocation(location, "792edf06f1f5ebcaf43632b55d8b03fe").enqueue(new Callback<List<Geolocation>>() {
            @Override
            public void onResponse(Call<List<Geolocation>> call, Response<List<Geolocation>> response) {
                binding.searchLocationButton.setEnabled(true);
                if (response.isSuccessful()){
                    List<Geolocation> geolocationsReceived = response.body();
                    Geolocation firstGeolocation = geolocationsReceived.get(0);
                    Log.d("msg-test", String.valueOf(firstGeolocation));

                    geolocationsFragment.add(firstGeolocation);
                    itemsViewModel.getGeolocations().setValue(geolocationsFragment);
                    geolocationAdapter.notifyItemInserted(geolocationsFragment.size());
                }
            }

            @Override
            public void onFailure(Call<List<Geolocation>> call, Throwable throwable) {
                binding.searchLocationButton.setEnabled(true);
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        if (sensorType == Sensor.TYPE_ACCELEROMETER){
            double xMod = Math.pow(Math.abs(event.values[0]), 2);
            double yMod = Math.pow(Math.abs(event.values[1]), 2);
            double zMod = Math.pow(Math.abs(event.values[2]), 2);
            double accelerometerModule = Math.sqrt(xMod+yMod+zMod);
            if (accelerometerModule >= 30 && !geolocationsFragment.isEmpty() && !dialogOpened){
                showUndoDialog();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void showUndoDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage("Deshacer acción");
        alertDialog.setPositiveButton("Deshacer", (dialog, which) -> {
            deleteLastLocationItem();
            dialog.dismiss();
            dialogOpened = false;
        });
        alertDialog.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
            dialogOpened = false;
        });
        alertDialog.show();
        dialogOpened = true;
    }
    private void deleteLastLocationItem(){
        int removedPosition = geolocationsFragment.size()-1;
        geolocationsFragment.remove(removedPosition);
        itemsViewModel.getGeolocations().setValue(geolocationsFragment);
        binding.locationRecycler.removeViewAt(removedPosition);
        geolocationAdapter.notifyItemRemoved(removedPosition);
    }
}