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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PedoWorker extends Worker implements SensorEventListener {
    private static final String TAG = "PedoWorker";

    private SensorManager sensorManager = null;
    private Sensor pedometer;
    private float totalSteps = 0;
    private float prevTotalSteps = 0;
    private int currentSteps = 0;

    public PedoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
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

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Started work");
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (pedometer != null) {
            sensorManager.registerListener(this, pedometer, SensorManager.SENSOR_DELAY_UI);
            Log.d(TAG, "Work successful");
        } else {
            Log.d(TAG, "No pedometer");
            return Result.retry();
        }

        return Result.success();
    }
}
