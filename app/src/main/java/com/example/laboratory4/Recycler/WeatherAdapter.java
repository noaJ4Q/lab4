package com.example.laboratory4.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratory4.Objetos.Weather;
import com.example.laboratory4.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>{
    public List<Weather> weathers;
    public Context context;

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather weather = weathers.get(position);
        holder.weather = weather;

        TextView nameText = holder.itemView.findViewById(R.id.locationText);
        TextView temperatureText = holder.itemView.findViewById(R.id.temperatureText);
        TextView tempMinText = holder.itemView.findViewById(R.id.tempMinText);
        TextView tempMaxText = holder.itemView.findViewById(R.id.tempMaxText);

        nameText.setText(weather.name);
        temperatureText.setText(weather.main.temp + " K");
        tempMinText.setText("Min: " + weather.main.temp_min + " K");
        tempMaxText.setText("Max: " + weather.main.temp_max + " K");
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        Weather weather;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public Context getContext() {
        return context;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
