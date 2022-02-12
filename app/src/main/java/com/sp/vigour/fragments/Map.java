package com.sp.vigour.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sp.vigour.GPSTracker;
import com.sp.vigour.R;
import com.sp.vigour.workers.AccelWorker;

import java.util.concurrent.TimeUnit;

public class Map extends Fragment implements OnMapReadyCallback , SensorEventListener {
    GPSTracker gpsTracker;
    double lat;
    double longi;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    PackageManager getPackageManager;
    GoogleMap map;
    LocationManager locationManager;
    Bundle bundle = this.getArguments();
    private BottomNavigationView navBar;
    FloatingActionButton qrFab;
    private float timestamp;
    private SensorManager sensorManager = null;
    private Sensor accelmeter;
    private TextView speed;
    private final float[] acceleration = new float[3];
    private final boolean invertAxisActive = false;
    private TextView math;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBar = getActivity().findViewById(R.id.bottomNavigationView);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelmeter = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (bundle != null) {
            lat = bundle.getDouble("lat");
            longi = bundle.getDouble("longi");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        speed = v.findViewById(R.id.userSpeed);
        math = v.findViewById(R.id.mathdisplay);
        math.setText(Html.fromHtml("s<sup>2</sup>"));
        gpsTracker = new GPSTracker(getContext());
        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.googleMaps);
        client = LocationServices.getFusedLocationProviderClient(getContext());
        supportMapFragment.getMapAsync(Map.this);
        qrFab = v.findViewById(R.id.qr_code);
        qrFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_map_to_qrcode);
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        navBar.setVisibility(View.GONE);
        if (accelmeter != null)
            sensorManager.registerListener(this, accelmeter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStop() {
        super.onStop();
        navBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.arraycopy(event.values, 0, acceleration, 0, event.values.length);

        // Invert the axes if desired.
        if (this.invertAxisActive)
        {
            acceleration[0] = -acceleration[0];
            acceleration[1] = -acceleration[1];
            acceleration[2] = -acceleration[2];
        }

        String currentSpd = Math.round(acceleration[0]) + "m/";
        speed.setText(currentSpd);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}