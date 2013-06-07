package com.liongrid.icarus;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewHighscoresActivity extends Activity {
	
	private TextView textView;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.view_highscore);
		textView =  (TextView) findViewById(R.id.highscore_view);
		populateListView();
		
		
	}

	private void populateListView() {
		new pageLoader().execute();
	}
	
	private class pageLoader extends AsyncTask<Object,Object,String>{

		@Override
		protected String doInBackground(Object... arg0) {
			return Utils.executeHttpPost("http://icarus.liongrid.com/get.php", "highscores","");
		}
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ViewHighscoresActivity.this.setProgressBarIndeterminateVisibility(true);
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			textView.setText(result);
			ViewHighscoresActivity.this.setProgressBarIndeterminateVisibility(false);
		}	
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			ViewHighscoresActivity.this.setProgressBarIndeterminateVisibility(false);
		}
	}
}
