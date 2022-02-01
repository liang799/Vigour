package com.sp.vigour;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccelWorker extends Worker implements SensorEventListener {

    private static final String TAG = "AccelWorker";

    private SensorManager sensorManager;
    private Sensor accelmeter;

    public AccelWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.d("acel", " X: "+ event.values[0] + " Y: "+ event.values[1]  + " Z: "+ event.values[2] );


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Started work");
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        accelmeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelmeter != null) {
            sensorManager.registerListener(this, accelmeter, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "Work successful");
        } else {
            Log.d(TAG, "No pedometer");
            return Result.retry();
        }

        return Result.success();
    }
}
