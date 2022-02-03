package com.sp.vigour.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sp.vigour.R;
import com.sp.vigour.workers.AccelWorker;

import java.util.concurrent.TimeUnit;

public class Map extends Fragment {

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
        return v;
    }
}