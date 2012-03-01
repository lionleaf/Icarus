package com.liongrid.icarus;

import java.util.Timer;

import android.R.bool;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.text.method.DateTimeKeyListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AccelerometerListener implements SensorEventListener {

	private final double EPSILON = 1;
	private ViewGroup rootView;
	//private TextView txtView; 

	private long startTimeStamp;
	private boolean timerRunning;
	private long lastAntiCheatStamp;
	private long longestDelay;

	public AccelerometerListener(ViewGroup rootView) {
		this.rootView = rootView;
		//txtView = (TextView) rootView.findViewById(R.id.textView); don't think we need this
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		double vLen = vectorLen(event.values);
		if(vLen < EPSILON){
			long stamp = System.nanoTime();

			lastAntiCheatStamp = stamp;
			if(!timerRunning){
				startTimeStamp = stamp;
				timerRunning = true;
			}else{
				long delay = stamp - lastAntiCheatStamp;
				if(delay > longestDelay){
					longestDelay = delay;
				}
			}
		}else{
			if(timerRunning){
				timerRunning = false;
				long nanoDuration = System.nanoTime();
				nanoDuration = nanoDuration - startTimeStamp;
				ResultManager.addResult(nanoDuration, longestDelay);

			}
		}
	}
	private double vectorLen(float[] vector){
		double squared_sum = 0;
		for (int i = 0; i < vector.length; i++) {
			squared_sum += vector[i] * vector[i];
		}
		return Math.sqrt(squared_sum);
	}
}
