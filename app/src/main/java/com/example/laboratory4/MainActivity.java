package com.example.laboratory4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.laboratory4.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.enterButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AppActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!haveInternetConnection()){
            showNoInternetDialog();
        }
    }

    private boolean haveInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void showNoInternetDialog() {
        MaterialAlertDialogBuilder materialDialog = new MaterialAlertDialogBuilder(this);
        materialDialog.setMessage("No se pudo establecer una conexión con internet");
        materialDialog.setPositiveButton("Ok", (dialog, which) -> {});
        materialDialog.setNeutralButton("Configuración", (dialog, which) -> {
            openConfiguration();
        });
        materialDialog.show();
    }

    private void openConfiguration(){
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }
}