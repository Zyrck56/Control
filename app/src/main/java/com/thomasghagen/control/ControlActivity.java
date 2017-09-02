package com.thomasghagen.control;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;

public class ControlActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    String S_ax, S_ay;
    double D_ax, D_ay;
    TextView xaxis, yaxis;
    Utils util;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        xaxis = (TextView)findViewById(R.id.xaxis);
        yaxis = (TextView)findViewById(R.id.yaxis);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {

            D_ax = event.values[0];
            D_ay = event.values[1];
            S_ax = String.valueOf(D_ax);
            S_ay = String.valueOf(D_ay);

                xaxis.setText(S_ax);
                yaxis.setText(S_ay);


            //util.sendData("192.168.0.33", 2300, S_ax + S_ay);


        }
    }

    private void setText(int x, int y) {
        xaxis.setText(x);
        yaxis.setText(y);
    }
}
