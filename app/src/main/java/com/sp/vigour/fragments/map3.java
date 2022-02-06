package com.sp.vigour.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class map3 extends Fragment implements OnMapReadyCallback {
    GPSTracker gpsTracker;
    double lat;
    double longi;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    PackageManager getPackageManager;
    GoogleMap map;
    LocationManager locationManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map3, container, false);
        gpsTracker = new GPSTracker(getContext());

        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.googleMaps);

        client = LocationServices.getFusedLocationProviderClient(getContext());
        supportMapFragment.getMapAsync(map3.this);

        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //lat = gpsTracker.getLatitude();
        //longi = gpsTracker.getLongitude();
        lat = 1.2540;
        longi = 103.8238;

        LatLng latLng = new LatLng(lat, longi);

        MarkerOptions options = new MarkerOptions().position(latLng);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        map.addMarker(options);
    }
}