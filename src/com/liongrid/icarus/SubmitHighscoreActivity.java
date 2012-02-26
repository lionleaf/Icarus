package com.liongrid.icarus;

import java.net.URLEncoder;

import org.json.JSONException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitHighscoreActivity extends Activity {

	public static final String HIGHSCORE_ID = "highscoreID";
	public static final String NICK_NAME = "nickname";
	private EditText nickInput;
	private EditText commentInput;
	private FlightResult flightResult;
	private CheckBox nick_cb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_highscore);
		nickInput = (EditText) findViewById(R.id.nickInput);
		commentInput = (EditText) findViewById(R.id.commentInput);
		
		flightResult = new FlightResult(this, getIntent().getLongExtra(HIGHSCORE_ID, -1));
		
		TextView highscoreTxt = (TextView) findViewById(R.id.highscoreText);
		highscoreTxt.setText(flightResult.getListString());
		nickInput.setText(loadNick());
		
		
	}
	
	public void submit(View v){
		String url;
		conditional_saveNick();
		try {
			url = "http://icarus.liongrid.com/add.php?action=addHighscore&data=";
			url += URLEncoder.encode(
					flightResult.getJSONString(nickInput.getText().toString(), commentInput.getText().toString()));
			String result = Utils.executeHttpGet(url);
			
			if(result.trim().equalsIgnoreCase(StaticVars.ACCEPT_STRING)){
				Toast toast = Toast.makeText(v.getContext(), "Highscore submitted", Toast.LENGTH_SHORT);
				toast.show();
				this.finish();
			}else{
				Toast toast = Toast.makeText(v.getContext(), result, Toast.LENGTH_SHORT);
				toast.show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			Toast toast = Toast.makeText(v.getContext(), "Could not connect", Toast.LENGTH_SHORT);
			toast.show();
			e.printStackTrace();
		}
	}
	
	public void conditional_saveNick(){
		nick_cb = (CheckBox) findViewById(R.id.nick_cb);
		if(nick_cb.isChecked()){
			SharedPreferences prefs  = getSharedPreferences(StaticVars.PREFS_NAME, 0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("Nickname", nickInput.getText().toString());

            // Commit the edits!
            editor.commit();

		}
	}
	
	public String loadNick(){
		SharedPreferences prefs  = getSharedPreferences(StaticVars.PREFS_NAME, 0);
		return prefs.getString("Nickname", "");		
	}
	
}
