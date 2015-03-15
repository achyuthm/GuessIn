package com.achyuthnfn.cnbfinal;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	Button startButton;
	Intent i;
	DatabaseAdapter databaseAdapter;
	TextView menuIconText,title,title2,introMessage;
	RelativeLayout menuIcon;
	Calendar calendar;
	Context mainContext=this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		databaseAdapter=new DatabaseAdapter(this);
		databaseAdapter.createDatabase();
		Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
		Typeface fontPromximaBold=Typeface.createFromAsset(getAssets(), "ProximaNova-Bold.otf");
		Typeface fontProximaRegular=Typeface.createFromAsset(getAssets(), "ProximaNova-Regular.otf");
		
		menuIconText=(TextView)findViewById(R.id.menuicontext);
		menuIconText.setTypeface(font);
		
		introMessage=(TextView)findViewById(R.id.header2);
		introMessage.setTypeface(fontProximaRegular);
		
		title=(TextView)findViewById(R.id.header1);
		title.setTypeface(fontPromximaBold);
		
		title2=(TextView)findViewById(R.id.header0);
		title2.setTypeface(fontPromximaBold);
		
		
		startButton=(Button)findViewById(R.id.btn_start);
		startButton.setTypeface(fontProximaRegular);
		startButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				calendar=Calendar.getInstance();
				calendar.add(Calendar.SECOND, 10);
				Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
				PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				AlarmManager alarmManager = (AlarmManager) mainContext.getSystemService(ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
				
				i=new Intent(MainActivity.this,GamePlay.class);
				startActivity(i);
			}
		});
		menuIcon=(RelativeLayout)findViewById(R.id.menuicon);
		menuIcon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
				intent.putExtra("GAMESTARTED", false);
				startActivity(intent);
//				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
