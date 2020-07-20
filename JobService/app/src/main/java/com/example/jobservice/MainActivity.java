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

		Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
		// Creat a PendingIntent to be triggered when the alarm goes off
		final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//setup periotic alarm every every 2 minute from this point onwards
		//show notify after 1minute
		Calendar calendar = Calendar.getInstance();
		int minute = calendar.get(Calendar.MINUTE);
		if (minute > 1) {
			return;
		}
		long delayMillis = (1 - minute) * 60 * 1000;

		long firstMillis = System.currentTimeMillis();

		long timeReopenMinute = firstMillis + DEFAULT_WATTING_SEND_NOTIFY * 1000;
		AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		// First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
		// Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
		if (alarmManager == null) {
			return;
		} else {
			alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, delayMillis, pIntent);
		}
		long timeStartOpen = ObjectPeference.getInstance(this).getData(START_OPEN_TIME, 0l);
		long timeReopen = System.currentTimeMillis() + DEFAULT_WATTING_SEND_NOTIFY * 60000L;
		ComponentName componentName = new ComponentName(this, TestJobService.class);
		JobInfo info = new JobInfo.Builder(JOB_ID, componentName)
				.setRequiresCharging(true)
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
				.setPersisted(true)
				.setMinimumLatency(delayMillis)
				.setOverrideDeadline(60000L)
				.build();
		JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
		int resultCode = scheduler.schedule(info);
		if (resultCode == JobScheduler.RESULT_SUCCESS) {
			Log.d(TAG, "Job scheduled");
		} else {
			Log.d(TAG, "Job scheduling failed");
		}
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
