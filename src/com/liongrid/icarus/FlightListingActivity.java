package com.liongrid.icarus;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FlightListingActivity extends Activity {

	
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flightlist);
		listView = (ListView) findViewById(R.id.flight_list_view);
		populateListView();
	}

	private void populateListView() {
		String query = "SELECT nanoduration, (strftime('%s', timestamp) * 1000), id FROM flight ORDER BY nanoduration DESC;";
		SQLOpener sqlOpen = new SQLOpener(this);
		SQLiteDatabase db = sqlOpen.getReadableDatabase();
		Cursor result = db.rawQuery(query, null);
		
		ArrayList<View> list = new ArrayList<View>();
		result.moveToFirst();
		int i = 0;
		while(!result.isAfterLast() && i++ < 20){
			long nanodur = result.getLong(0);
			long timestamp = result.getLong(1);
			long id = result.getLong(2);
			FlightResult res = new FlightResult(nanodur, timestamp, id);
			FlightResultView frv = new FlightResultView(this, res);
			list.add(frv);
			result.moveToNext();
		}
		
		db.close();		
		listView.setAdapter(new ArrayAdapter<View>(this, R.layout.list_view_text, list));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				((FlightResultView) parent.getAdapter().getItem(pos)).addHighScore();
				
			}
		});
	}
}
