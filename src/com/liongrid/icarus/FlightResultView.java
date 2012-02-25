package com.liongrid.icarus;

import java.sql.Timestamp;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlightResultView extends LinearLayout {

	private FlightResult flightResult;
	private Context context;

	public FlightResultView(Context context, FlightResult flightResult) {
		super(context);
		this.flightResult = flightResult;
		this.context = context;
		
		setOrientation(VERTICAL);
		populate(context);
	}

	private void populate(Context context) {
		TextView txtView = new TextView(context);
		txtView.setText(ResultManager.formatTime(flightResult.getNanoduration()));
		txtView.setTextSize(25f);
		addView(txtView);
		
		txtView = new TextView(context);
		txtView.setText((new Timestamp(flightResult.getTimestamp())).toLocaleString());
		addView(txtView);
	}
	
	public String toString(){
		return flightResult.getListString();
	}

	public FlightResult getFlightResult(){
		return flightResult;
	}

	public void addHighScore() {
		Intent i = new Intent(context, SubmitHighscoreActivity.class);
		i.putExtra(SubmitHighscoreActivity.HIGHSCORE_ID, flightResult.getId() );
		context.startActivity(i);
	}
}
