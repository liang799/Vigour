package com.sp.vigour;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Debugging extends Fragment implements SensorEventListener {
    private SensorManager sensorManager = null;
    private Sensor pedometer;
    private ImageView cross;
    private TextView error_msg;
    private boolean running = false;
    private float totalSteps = 0;
    private float prevTotalSteps = 0;
    private TextView steps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_debugging_counter, container, false);
        cross = v.findViewById(R.id.error_counter);
        error_msg = v.findViewById(R.id.error_counter_msg);
        steps = v.findViewById(R.id.counter_int);
        if (pedometer != null){
            // Success! There's a pedometer.
            cross.setVisibility(View.GONE);
            error_msg.setVisibility(View.GONE);
        }

        if (ContextCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACTIVITY_RECOGNITION) ==
                PackageManager.PERMISSION_GRANTED) {
        } else {
            error_msg.setVisibility(View.VISIBLE);
            error_msg.setText("Physical Activity Permission needed!");
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        running = true;
        Sensor pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Toast.makeText(getActivity(), "running is true", Toast.LENGTH_SHORT).show();
        if (pedometer != null)
            sensorManager.registerListener(this, pedometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0];
            int currentSteps = Math.round(totalSteps) - Math.round(prevTotalSteps);
            steps.setText(String.valueOf(currentSteps));
            cross.setVisibility(View.GONE);
            error_msg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}