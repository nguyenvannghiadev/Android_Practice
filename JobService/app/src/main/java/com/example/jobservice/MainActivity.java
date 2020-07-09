package com.example.jobservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jobservice.service.TestJobService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = "MainActivity";
	private static final int JOB_ID = 123;
	private Button btnStartJob, btnCanceJob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnStartJob = findViewById(R.id.start_job);
		btnCanceJob = findViewById(R.id.cance_job);
		btnStartJob.setOnClickListener(this);
		btnCanceJob.setOnClickListener(this);
	}

	public void scheduleJob(View view) {
		ComponentName componentName = new ComponentName(this, TestJobService.class);
		JobInfo info = new JobInfo.Builder(JOB_ID, componentName)
				.setRequiresCharging(true)
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
				.setPersisted(true)
				.setMinimumLatency(1000)
				.setOverrideDeadline(2*1000)
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
		if (vId == R.id.start_job) {
			scheduleJob(v);
		} else if (vId == R.id.cance_job){
			canceJob(v);
		}
	}
}
