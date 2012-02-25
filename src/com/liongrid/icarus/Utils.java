package com.liongrid.icarus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Utils {

	public static String executeHttpGet(String url) throws Exception {
		Log.d("Icarus", "Executing http get request!  "+url);
	    BufferedReader in = null;
	    try {
	        HttpClient client = new DefaultHttpClient();
	        HttpGet request = new HttpGet();
	        request.setURI(new URI(url));
	        HttpResponse response = client.execute(request);
	        in = new BufferedReader
	        (new InputStreamReader(response.getEntity().getContent()));
	        StringBuffer sb = new StringBuffer("");
	        String line = "";
	        String NL = System.getProperty("line.separator");
	        while ((line = in.readLine()) != null) {
	            sb.append(line + NL);
	        }
	        in.close();
	        String page = sb.toString();
	        Log.d("Icarus", "Server response: "+page);
	        return page;
	        } finally {
	        if (in != null) {
	            try {
	                in.close();
	                } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

}
