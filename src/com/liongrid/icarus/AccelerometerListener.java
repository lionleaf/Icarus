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
	private double min_val = 9001;
	private TextView txtView;
	
	private long lastTimeStamp;
	private boolean timerRunning;
	private double lastDuration;
	private double maxDuration;
	
	
	public AccelerometerListener(ViewGroup rootView) {
		this.rootView = rootView;
		txtView = (TextView) rootView.findViewById(R.id.textView);
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		double vLen = vectorLen(event.values);
		if(vLen < min_val){
			min_val =vLen; 
		}
		if(vectorLen(event.values) < EPSILON){
			rootView.setBackgroundColor(Color.WHITE);
			if(!timerRunning){
				lastTimeStamp = System.nanoTime(); 
				timerRunning = true;
			}
		}else{
			if(timerRunning){
				timerRunning = false;
				long temp = System.nanoTime();
				temp = temp - lastTimeStamp;
				lastDuration = temp * 0.000000001;
				if(lastDuration > maxDuration){
					maxDuration = lastDuration;
					txtView.setText("Max duration: "+maxDuration);
				}
					txtView.setText("Max duration: "+maxDuration);
			}
			rootView.setBackgroundColor(Color.BLACK);
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
