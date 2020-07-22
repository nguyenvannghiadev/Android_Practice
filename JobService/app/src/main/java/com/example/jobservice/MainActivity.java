package com.example.jobservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jobservice.receiver.MyAlarmReceiver;
import com.example.jobservice.reference.ObjectPeference;
import com.example.jobservice.service.TestJobService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = "MainActivity";
	private static final int JOB_ID = 123;
	private static final long DEFAULT_WATTING_SEND_NOTIFY = 60L;
	public static final String START_OPEN_TIME = "StartOpenTime";
	private Button btnStartJob, btnCanceJob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnCanceJob = findViewById(R.id.cance_job);
		btnCanceJob.setOnClickListener(this);
		//
		persistOpenDate();
		scheduleJob();
	}

	public void scheduleJob() {
		//alrmService
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 15);
		Intent intent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
		intent.addCategory("android.intent.category.DEFAULT");
		PendingIntent broadcast = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,intent , PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),broadcast);

	}

	public void canceJob(View view) {
		JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
		scheduler.cancel(JOB_ID);
		Log.d(TAG, " Job cancelled");
	}

	@Override
	public void onClick(View v) {
		int vId = v.getId();
		if (vId == R.id.cance_job) {
			canceJob(v);
		}
	}

	private void persistOpenDate() {
		try {
			ObjectPeference peference = ObjectPeference.getInstance(this);
			if (peference.getData(START_OPEN_TIME, 0l) <= 0) {
				peference.saveData(START_OPEN_TIME, System.currentTimeMillis());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
