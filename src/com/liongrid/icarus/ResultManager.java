package com.liongrid.icarus;

import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class ResultManager {

	private static final String ADD_RESULT_PAGE = "http://icarus.liongrid.com/add.php";
	private static double lastDuration;
	private static double maxDuration;
	
	public static void addResult(long nanoDuration){
		
		lastDuration = nanoDuration * 0.000000001;
		if(lastDuration > maxDuration){
			maxDuration = lastDuration;
		}
		FlyActivity.current.setResultText("Max duration this session: "+formatTime(maxDuration) 
				+"\nLast duration: "+formatTime(lastDuration));
		addResultToDB(nanoDuration);
		if(isConnected(FlyActivity.current) && !UploadToServerTask.isRunning){
			new UploadToServerTask().execute(null);
		}	

	}
	
	private static boolean isConnected(Context context) {
	    ConnectivityManager connectivityManager = (ConnectivityManager)
	        context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = null;
	    if (connectivityManager != null) {
	        networkInfo =
	            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    }
	    return networkInfo == null ? false : networkInfo.isConnected();
	}
	

	public static String formatTime(double time){
		String timeString = "";
		if(time >= 1.){
			timeString = ((int)time)+"s and ";
		}
		timeString += (int)((time - (int)time)*1000) + "ms";
		return timeString;
	}
	
	public static String formatTime(long nanotime){
		return formatTime(nanotime * 0.000000001);
	}
	
	private static void addResultToDB(long nanoDuration){
		String sql = "INSERT INTO flight(nanoduration) VALUES("+nanoDuration+");";
		
		SQLOpener sqlOpen = new SQLOpener(FlyActivity.current);
		SQLiteDatabase db = sqlOpen.getWritableDatabase();
		db.execSQL(sql);
		db.close();	
	}
	public static void sendUnsentResults(){
		String query = "SELECT (strftime('%s', timestamp) * 1000) AS timestamp, nanoduration, ID " +
				"FROM flight WHERE uploaded = 0;";
		SQLOpener sqlOpen = new SQLOpener(FlyActivity.current);
		SQLiteDatabase db = sqlOpen.getWritableDatabase();
		Cursor result = db.rawQuery(query, null);
		String uploadString;
		try {
			uploadString = generateResultJSON(result);
			if(uploadResult(uploadString)){
				setAllToUploaded(db);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();	
	}

	private static void setAllToUploaded(SQLiteDatabase db) {
		Log.d("Icarus", "Setting uploaded state in local db");
		String sql = "UPDATE flight SET uploaded = 1 WHERE uploaded = 0";
		db.execSQL(sql);
	}

	private static boolean uploadResult(String uploadString) throws Exception {
		String resultString = Utils.executeHttpPost(ADD_RESULT_PAGE,"addResults",URLEncoder.encode(uploadString));
		Log.d("Icarus", "Server response: "+resultString);
		if(resultString.trim().equalsIgnoreCase(StaticVars.ACCEPT_STRING)){
			return true;
		}
		return false;
	}

	
	
	
	private static String generateResultJSON(Cursor result) throws JSONException {
		Log.d("Icarus", "generating result JSON");
		JSONArray resultArr = new JSONArray();
		result.moveToFirst();
		while(!result.isAfterLast()) {
			JSONObject jsonObj = new JSONObject();
			
			 long millis = result.getLong(0);
			 Timestamp tStamp = new Timestamp(millis);
			
			jsonObj.put("timestamp", tStamp.toString().replaceAll("\\.[0-9]*", ""));// + " "+addedOn.getHours()+":" + addedOn.getMinutes()+":"+addedOn.getSeconds());
			
			jsonObj.put("duration", result.getString(1));
			jsonObj.put("uniqueId", StaticVars.UID);
			jsonObj.put("localId", result.getInt(2));
			resultArr.put(jsonObj);
			result.moveToNext();
		}
		return resultArr.toString();
	}
}
