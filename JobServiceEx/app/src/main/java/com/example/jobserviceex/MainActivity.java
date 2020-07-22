package com.example.jobserviceex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jobserviceex.notify.NotificationManager;

public class MainActivity extends AppCompatActivity {
	public static final String NOTIFICATION_CHANNEL_ID = "ex_notify_channel";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		NotificationManager.scheduleJob(getApplicationContext());
	}
}
