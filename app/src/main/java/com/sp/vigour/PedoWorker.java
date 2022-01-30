package com.sp.vigour;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.ForegroundInfo;
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
    private float prevSteps = 0;
    private String currentSteps;
    private String today;
    private SimpleDateFormat simpleDateFormat;
    private Addhelper helper = null;

    public PedoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        totalSteps = event.values[0];
        helper = new Addhelper(getApplicationContext());
        if (!helper.checkForTables()) {
            //create new entry
            currentSteps = String.valueOf(Math.round(totalSteps));
            helper.insert(currentSteps, today);
        } else if(simpleDateFormat.format(new Date()) != today) {
            //update date, steps and create new entry
            today = simpleDateFormat.format(new Date());
            prevSteps = totalSteps;
            currentSteps = String.valueOf(Math.round(totalSteps) - Math.round(prevSteps));
            helper.insert(currentSteps, today);
        } else {
            //use old entry
            currentSteps = String.valueOf(Math.round(totalSteps) - Math.round(prevSteps));
            helper.updateSteps(currentSteps, today);
        }
        //Toast.makeText(getApplicationContext(), currentSteps + " Steps, " + today, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Started work");
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (pedometer != null) {
            setForegroundAsync(createForegroundInfo("Started"));
            simpleDateFormat = new SimpleDateFormat("dd LLL");
            today = simpleDateFormat.format(new Date());
            sensorManager.registerListener(this, pedometer, SensorManager.SENSOR_DELAY_UI);
            while(isUSBCharging());
            Log.d(TAG, "Work successful");
        } else {
            Log.d(TAG, "No pedometer");
            return Result.retry();
        }

        return Result.success();
    }

    @NonNull
    private ForegroundInfo createForegroundInfo(@NonNull String progress) {
        // Build a notification using bytesRead and contentLength

        Context context = getApplicationContext();
        String id = "pedoNotifChannel";
        String title = "Vigour Pedometer";
        String cancel = "Cancel?";
        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(title)
                .setContentText("Charge the phone to stop the pedometer")
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_logo_navdrawer)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build();

        return new ForegroundInfo(1, notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        CharSequence name = "Pedometer is now active";
        String description = "Tracking the amount of steps you have taken";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("pedoNotifChannel", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    BatteryManager myBatteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isUSBCharging(){
        return  myBatteryManager.isCharging();
    }
}
