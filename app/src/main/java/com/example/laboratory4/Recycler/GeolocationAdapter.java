package com.example.laboratory4.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratory4.Fragments.CityFragment;
import com.example.laboratory4.Objetos.Geolocation;
import com.example.laboratory4.Objetos.Weather;
import com.example.laboratory4.R;
import com.example.laboratory4.Services.OpenWeatherService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeolocationAdapter extends RecyclerView.Adapter<GeolocationAdapter.GeolocationViewHolder>{
    public FragmentActivity fragmentActivity;
    public List<Geolocation> geolocations;
    public Context context;


    @NonNull
    @Override
    public GeolocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
        return new GeolocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeolocationViewHolder holder, int position) {
        Geolocation geolocation = geolocations.get(position);
        holder.geolocation = geolocation;

        TextView nameText = holder.itemView.findViewById(R.id.geolocationNameText);
        TextView latText = holder.itemView.findViewById(R.id.geolocationLatText);
        TextView lonText = holder.itemView.findViewById(R.id.geolocationLonText);

        nameText.setText(geolocation.name);
        latText.setText("Lat: " + geolocation.lat);
        lonText.setText("Lon: " + geolocation.lon);
    }

    @Override
    public int getItemCount() {
        return geolocations.size();
    }

    public class GeolocationViewHolder extends RecyclerView.ViewHolder{

        Geolocation geolocation;
        public GeolocationViewHolder(@NonNull View itemView) {
            super(itemView);
            Button weatherButton = itemView.findViewById(R.id.weatherButton);
            weatherButton.setOnClickListener(v -> {
                getWeatherFromGeolocation(geolocation);
            });
        }
    }

    private void getWeatherFromGeolocation(Geolocation geolocation){
        OpenWeatherService openWeatherService = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherService.class);

        openWeatherService.getWeather(geolocation.lat, geolocation.lon, "792edf06f1f5ebcaf43632b55d8b03fe").enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful()){
                    Weather weather = response.body();

                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public List<Geolocation> getGeolocations() {
        return geolocations;
    }

    public void setGeolocations(List<Geolocation> geolocations) {
        this.geolocations = geolocations;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
