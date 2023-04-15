package com.example.ejemplolocalizacion;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solicitarPermisos();

        //obtenerUltimaUbicacion();
        lecturaPermanente();



    }

    @SuppressLint("MissingPermission")
    private void lecturaPermanente() {
        LocationRequest request=LocationRequest.create();
        request.setInterval(5000);
        request.setFastestInterval(500);
        request.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        LocationCallback callback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Log.d("Salida", "Leyendo...");
                if (locationResult == null) {
                    return;
                }
                List<Location> lista = locationResult.getLocations();
                for (Location ubicacion : lista) {
                    Toast.makeText(MainActivity.this, "Lat " + ubicacion.getLongitude() + "Long " + ubicacion.getLongitude(), Toast.LENGTH_LONG).show();

                }
            }
        };

        fused=LocationServices.getFusedLocationProviderClient(this);
        fused.requestLocationUpdates(request,callback,null);

        
    }

    private void obtenerUltimaUbicacion() {

        fused = LocationServices.getFusedLocationProviderClient(this);
        @SuppressLint("MissingPermission") Task<Location> tarea=fused.getLastLocation();
        tarea.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    Log.d("Salida Latitud",location.getLatitude()+"");
                    Log.d("Salida Longitud",location.getLongitude()+"");
                }
            }
        });
        Log.d("Salida hilo principal", "Salida");
    }

    public void solicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (checkSelfPermission(ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED||
                checkSelfPermission(ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, 1000);
        }
    }
}