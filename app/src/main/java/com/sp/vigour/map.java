package com.sp.vigour;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class map extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.googleMaps);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        //Set position of marker
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                        googleMap.clear();

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });

        // Return view
        return view;
    }
}