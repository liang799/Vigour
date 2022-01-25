package com.sp.vigour;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NavDrawerActivity extends AppCompatActivity implements SensorEventListener {
    private DrawerLayout drawer;
    private AppBarConfiguration appBarConfiguration;
    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 45;
    private SensorManager sensorManager = null;
    private Sensor pedometer;
    private float totalSteps = 0;
    private float prevTotalSteps = 0;
    private int currentSteps = 0;
    private boolean running = false;

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
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        } else {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        running = true;
        Sensor pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (pedometer != null)
            sensorManager.registerListener(this, pedometer, SensorManager.SENSOR_DELAY_UI);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0];
            currentSteps = Math.round(totalSteps) - Math.round(prevTotalSteps);
            SimpleDateFormat sdf = new SimpleDateFormat("dd LLL");
            String today = sdf.format(new Date());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}