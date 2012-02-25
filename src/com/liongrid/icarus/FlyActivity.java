package com.liongrid.icarus;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FlyActivity extends Activity {
    private static TextView textView;
	private SensorManager sensorMng;
	private ViewGroup rootView;
	public static FlyActivity current;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        current = this;
        
        textView = (TextView) findViewById(R.id.textView);
        rootView = (ViewGroup) findViewById(R.id.rootLayout);
        
        sensorMng = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMng.registerListener(new AccelerometerListener(rootView), 
        		sensorMng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        
    }

	public void setResultText(String string) {
		textView.setText(string);
	}
}