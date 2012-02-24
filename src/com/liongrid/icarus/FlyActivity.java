package com.liongrid.icarus;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.ViewGroup;

public class FlyActivity extends Activity {
    private SensorManager sensorMng;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ViewGroup rootView = (ViewGroup) findViewById(R.id.rootLayout);
        rootView.setBackgroundColor(Color.RED);
        sensorMng = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMng.registerListener(new AccelerometerListener(rootView), 
        		sensorMng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        
    }
}