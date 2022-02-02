package com.sp.vigour.activities;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sp.vigour.PedoWorker;
import com.sp.vigour.AccelWorker;
import com.sp.vigour.R;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private AppBarConfiguration appBarConfiguration;
    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        /* ---- Binding Views ---- */
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationView navigationView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this,  R.id.fragment_container);

        /* ---- Navigation using NavGraph ---- */
        appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.home2, R.id.steps, R.id.transactions)
                        .setDrawerLayout(drawer).build(); //up button will not be displayed for these destinations
        setSupportActionBar(toolbar);
        setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);

        /* --------  Permissions --------- */
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            //ask for permission
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        }

        /* --------  Schedule Work --------- */
        WorkRequest pedometerChecker =
                new OneTimeWorkRequest.Builder(PedoWorker.class)
                        .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.MILLISECONDS)
                        .build();

        PeriodicWorkRequest pedometer =
                new PeriodicWorkRequest.Builder(PedoWorker.class, 15, TimeUnit.MINUTES)
                        // Constraints
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "Pedometer",
                ExistingPeriodicWorkPolicy.KEEP,
                pedometer);
        WorkManager.getInstance().enqueueUniqueWork(
                "pedomChecker",
                ExistingWorkPolicy.REPLACE,
                (OneTimeWorkRequest) pedometerChecker);

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

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "accelerometer",
                ExistingPeriodicWorkPolicy.KEEP,
                accel);
        WorkManager.getInstance().enqueueUniqueWork(
                "accelChecker",
                ExistingWorkPolicy.REPLACE,
                (OneTimeWorkRequest) accelChecker);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

}