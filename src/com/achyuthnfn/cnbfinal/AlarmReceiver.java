package com.achyuthnfn.cnbfinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver
{
	NotificationManagerClass managerClass;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		managerClass=new NotificationManagerClass(context);
		managerClass.createNotification();
	}
	
}
