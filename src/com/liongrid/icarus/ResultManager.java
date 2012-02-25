package com.liongrid.icarus;

import android.database.sqlite.SQLiteDatabase;

public class ResultManager {

	private static double lastDuration;
	private static double maxDuration;
	public static void addResult(long nanoDuration){
		addResultToDB(nanoDuration);
		lastDuration = nanoDuration * 0.000000001;
		if(lastDuration > maxDuration){
			maxDuration = lastDuration;
		}
		FlyActivity.current.setResultText("Max duration: "+formatTime(maxDuration) 
				+"\nLast duration: "+formatTime(lastDuration)
				+"\nDrop distance: "+getDistance(0.5*9.81*(lastDuration*lastDuration)));

	}
	
	private static String getDistance(double time) {
		double distance = (0.5*9.81*(time*time));
		String distanceText = ""; 
		if(distance >= 1.){
			distanceText = (int)distance + "m and ";
		}
		distanceText += (int)((distance-(int)distance)*100) + "cm";
		return distanceText;
	}

	private static String formatTime(double time){
		String timeString = "";
		if(time >= 1.){
			timeString = ((int)time)+"s and ";
		}
		timeString += (int)((time - (int)time)*100) + "ms";
		return timeString;
	}
	
	private static void addResultToDB(long nanoDuration){
		String sql = "INSERT INTO result(nanoduration) VALUES("+nanoDuration+");";
		
		SQLOpener sqlOpen = new SQLOpener(FlyActivity.current);
		SQLiteDatabase db = sqlOpen.getWritableDatabase();
		db.execSQL(sql);
		db.close();	
	}
	
}
