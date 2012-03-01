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

	private long startTimeStamp;
	private boolean timerRunning;
	private long lastTimeStamp;
	private long delay;

	public AccelerometerListener(ViewGroup rootView) {
		this.rootView = rootView;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		double vLen = vectorLen(event.values);
		if(vLen < EPSILON){
			long stamp = System.nanoTime();

			if(!timerRunning){
				startTimeStamp = stamp;
				timerRunning = true;
				lastTimeStamp = stamp;
			}else{
				lastTimeStamp = stamp;
			}
		}else{
			if(timerRunning){
				timerRunning = false;
				long stamp = System.nanoTime();

				delay = stamp - lastTimeStamp;
				stamp = stamp - startTimeStamp;
				ResultManager.addResult(stamp, delay);
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
