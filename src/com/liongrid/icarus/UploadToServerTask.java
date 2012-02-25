package com.liongrid.icarus;

import android.os.AsyncTask;

public class UploadToServerTask  extends AsyncTask{

	public static boolean isRunning;

	@Override
	protected Object doInBackground(Object... params) {
		ResultManager.sendUnsentResults();
		return null;
	}

	
	@Override
	protected void onPostExecute(Object result) {
		isRunning = false;
	}
	
	@Override
	protected void onPreExecute() {
		isRunning = true;
	}
	
	@Override
	protected void onProgressUpdate(Object... values) {
	}
	
	@Override
	protected void onCancelled() {
		isRunning = false;
	}
}
