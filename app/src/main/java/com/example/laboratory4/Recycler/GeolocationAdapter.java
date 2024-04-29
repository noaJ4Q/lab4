package com.example.laboratory4.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratory4.Fragments.CityFragment;
import com.example.laboratory4.Objetos.Geolocation;
import com.example.laboratory4.R;

import java.util.List;

public class GeolocationAdapter extends RecyclerView.Adapter<GeolocationAdapter.GeolocationViewHolder>{
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
        }
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
