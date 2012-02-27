package com.liongrid.icarus;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FlyActivity extends Activity {
	private static TextView maxDurationLabel,maxDuration,lastDurationLabel,lastDuration;
	private SensorManager sensorMng;
	private ViewGroup rootView;
	public static FlyActivity current;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        current = this;
        
        maxDurationLabel = (TextView) findViewById(R.id.maxDurationLabel);
        maxDuration = (TextView) findViewById(R.id.maxDuration);
        lastDurationLabel = (TextView) findViewById(R.id.lastDurationLabel);
        lastDuration = (TextView) findViewById(R.id.lastDuration);
        rootView = (ViewGroup) findViewById(R.id.rootLayout);
        
        sensorMng = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMng.registerListener(new AccelerometerListener(rootView), 
        		sensorMng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        
        
                
    }

	public void setResultText(String maxDuration, String lastDuration) {
		FlyActivity.maxDurationLabel.setText("Max duration \n this session:");
		FlyActivity.maxDuration.setText(maxDuration);
		FlyActivity.lastDurationLabel.setText("Last Duration:");
		FlyActivity.lastDuration.setText(lastDuration);
	}

	public void showUncertantyError() {
		FlyActivity.lastDurationLabel.setText("Last Duration (disqualified due to too bad readings):");
	}

	public void showDebugText(String string) {
		lastDurationLabel.setText(lastDuration.getText()+string);
		// TODO Auto-generated method stub
		
	}
}