package com.sp.vigour;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Pedometer extends Service implements SensorEventListener {
    private static final String TAG = "PedomService";

    private SensorManager sensorManager = null;
    private Sensor pedometer;
    private float totalSteps = 0;
    private float prevTotalSteps = 0;
    private int currentSteps = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (pedometer != null)
            sensorManager.registerListener(this, pedometer, SensorManager.SENSOR_DELAY_UI);
        else
            Log.e(TAG, "Pedometer == null");

        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        totalSteps = event.values[0];
        currentSteps = Math.round(totalSteps) - Math.round(prevTotalSteps);
        SimpleDateFormat sdf = new SimpleDateFormat("dd LLL");
        String today = sdf.format(new Date());
        Toast.makeText(getApplicationContext(), String.valueOf(currentSteps) + " Steps, " + today, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
