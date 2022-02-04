package com.sp.vigour.workers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class AccelWorker extends Worker implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelmeter;

    public AccelWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Log.d("accel", " X: "+ event.values[0] + " Y: "+ event.values[1]  + " Z: "+ event.values[2] );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @NonNull
    @Override
    public Result doWork() {
        //Log.d("accel", "Started work");
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        accelmeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelmeter != null) {
            sensorManager.registerListener(this, accelmeter, SensorManager.SENSOR_DELAY_NORMAL);
           // Log.d("accel", "Work successful accel");
        } else {
            //Log.d("accel", "No accel");
            return Result.retry();
        }

        return Result.success();
    }
}
