package com.example.jobservice.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.jobservice.NotificationActivity;
import com.example.jobservice.R;
import com.example.jobservice.service.TestJobService;

public class MyAlarmReceiver extends BroadcastReceiver {
	public static final int REQUEST_CODE = 12345;
	public static final String ACTION = "example.servicesdemo.alarm";
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent notificationIntent = new Intent(context, NotificationActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(NotificationActivity.class);
		stackBuilder.addNextIntent(notificationIntent);
		//
		PendingIntent pendingIntent = stackBuilder.getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		Notification notification = builder.setContentTitle("Demo App Notification")
				.setContentText("New Notification From Demo App...")
				.setTicker("New Massege Alert")
				.setAutoCancel(true)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentIntent(pendingIntent).build();
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0,notification);


	}
}
