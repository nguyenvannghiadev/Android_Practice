package com.example.jobservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jobservice.service.TestJobService;

public class MyAlarmReceiver extends BroadcastReceiver {
	public static final int REQUEST_CODE = 12345;
	public static final String ACTION = "example.servicesdemo.alarm";
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, TestJobService.class);
		i.putExtra("foo", "bar");
		context.startService(i);

	}
}
