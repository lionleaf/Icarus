package com.liongrid.icarus;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

public class FlightResult {
	private long nanoduration;
	private long timestamp;
	private long id;

	public FlightResult(long nanodur, long timestamp, long id) {
		this.nanoduration = nanodur;
		this.timestamp = timestamp;
		this.id = id;
	}

	/**
	 * Will fetch from DB.
	 * @param id
	 */
	public FlightResult(Context context, long id) {
		String SQL = "SELECT nanoduration, (strftime('%s', timestamp) * 1000) FROM flight WHERE ID = "+id+";";
		SQLOpener sqlOpen = new SQLOpener(context);
		SQLiteDatabase db = sqlOpen.getReadableDatabase();
		Cursor result = db.rawQuery(SQL, null);
		result.moveToFirst();
		nanoduration = result.getLong(0);
		timestamp = result.getLong(1);
		db.close();
		this.id = id;
	}

	public long getNanoduration() {
		return nanoduration;
	}
	public void setNanoduration(long nanoduration) {
		this.nanoduration = nanoduration;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getListString() {
		String string = "Duration: "+ ResultManager.formatTime(nanoduration) + " \n";
		string += "flown at "+(new Timestamp(timestamp)).toLocaleString();
		return string;
	}

	public String getJSONString(String nick, String comment) throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put("nickname", nick);
		obj.put("description",comment);
		obj.put("localId", id);
		obj.put("duration", nanoduration);
		obj.put("uniqueId", StaticVars.UID);

		Timestamp tStamp = new Timestamp(timestamp);
		obj.put("timestamp", tStamp.toString().replaceAll("\\.[0-9]*", "")); 
		
		return obj.toString();

	}
}
