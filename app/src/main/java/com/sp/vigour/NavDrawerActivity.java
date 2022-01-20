package com.sp.vigour;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class NavDrawerActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager = null;
    private boolean running = false;
    private float totalSteps = 0;
    private float prevTotalSteps = 0;


    private DrawerLayout drawer;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationView navigationView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this,  R.id.fragment_container);

        appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.home2, R.id.steps, R.id.transactions)
                        .setDrawerLayout(drawer).build(); //up button will not be displayed for these destinations
        setSupportActionBar(toolbar);
        setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

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
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (pedometer == null)
            Toast.makeText(getApplicationContext(), "ERROR - No Pedometer", Toast.LENGTH_SHORT).show();
        else
            sensorManager.registerListener(this, pedometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0];
            int currentSteps = Math.round(totalSteps) - Math.round(prevTotalSteps);
            try {
                TextView steps = findViewById(R.id.counter_int);
                ImageView crossOut_steps = findViewById(R.id.error_counter);
                TextView stepErrMsg = findViewById(R.id.error_counter_msg);
                steps.setText(String.valueOf(totalSteps));
                crossOut_steps.setVisibility(View.GONE);
                stepErrMsg.setVisibility(View.GONE);
            } catch (Exception e) {
                // This will catch any exception, because they are all descended from Exception
                System.out.println("Error " + e.getMessage());
                return;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}