package com.achyuthnfn.cnbfinal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class NotificationSevice extends Service 
{

	private NotificationManager manager;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		this.getApplicationContext();
		manager=(NotificationManager)this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		
		Intent mainActivityIntent=new Intent(this.getApplicationContext(), MainActivity.class);
		Notification notification=new Notification(R.drawable.app_icon, "Your smart phone has thought of a word for you. Can you guess it?", System.currentTimeMillis());
		mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		PendingIntent pendingIntentNotification=PendingIntent.getActivity(this.getApplicationContext(), 0, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags|=Notification.FLAG_AUTO_CANCEL;
		
		notification.setLatestEventInfo(this.getApplicationContext(), "GuessIn", "Your smart phone has thought of a word for you. Can you guess it?", pendingIntentNotification);
		
		manager.notify(0, notification);
	}

}
