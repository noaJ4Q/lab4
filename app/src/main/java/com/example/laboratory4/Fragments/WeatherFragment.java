package com.example.laboratory4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.laboratory4.R;

public class WeatherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        NavController navController = NavHostFragment.findNavController(WeatherFragment.this);
        Button goSearchLocationButton = getActivity().findViewById(R.id.goSearchLocationButton);
        goSearchLocationButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_weatherFragment_to_cityFragment);
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }
}