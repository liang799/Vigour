package com.sp.vigour.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sp.vigour.GPSTracker;
import com.sp.vigour.R;
import com.sp.vigour.workers.AccelWorker;

import java.util.concurrent.TimeUnit;

public class Map extends Fragment implements OnMapReadyCallback {
    GPSTracker gpsTracker;
    double lat;
    double longi;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    PackageManager getPackageManager;
    GoogleMap map;
    LocationManager locationManager;
    Bundle bundle = this.getArguments();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* --------  Schedule accel --------- */
        WorkRequest accelChecker =
                new OneTimeWorkRequest.Builder(AccelWorker.class)
                        .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.MILLISECONDS)
                        .build();

        PeriodicWorkRequest accel =
                new PeriodicWorkRequest.Builder(AccelWorker.class, 1, TimeUnit.SECONDS)
                        // Constraints
                        .build();

        WorkManager.getInstance(getContext()).enqueueUniquePeriodicWork(
                "accelerometer",
                ExistingPeriodicWorkPolicy.KEEP,
                accel);
        WorkManager.getInstance().enqueueUniqueWork(
                "accelChecker",
                ExistingWorkPolicy.REPLACE,
                (OneTimeWorkRequest) accelChecker);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        gpsTracker = new GPSTracker(getContext());

        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.googleMaps);

        client = LocationServices.getFusedLocationProviderClient(getContext());
        supportMapFragment.getMapAsync(Map.this);

        if (bundle != null) {
            lat = bundle.getDouble("lat");
            longi = bundle.getDouble("longi");
        }

        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //lat = gpsTracker.getLatitude();
        //longi = gpsTracker.getLongitude();
        lat = 1.3385;
        longi = 103.7304;

        LatLng latLng = new LatLng(lat, longi);

        MarkerOptions options = new MarkerOptions().position(latLng);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        map.addMarker(options);
    }
}