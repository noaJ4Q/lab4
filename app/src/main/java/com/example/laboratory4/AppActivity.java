package com.example.laboratory4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.laboratory4.Objetos.Geolocation;
import com.example.laboratory4.Objetos.Weather;

import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity {
    List<Geolocation> geolocations = new ArrayList<>();
    List<Weather> weathers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public List<Geolocation> getGeolocations() {
        return geolocations;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setGeolocations(List<Geolocation> geolocations) {
        this.geolocations = geolocations;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }
}