package com.example.jobserviceex.notify;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.jobserviceex.model.Notify;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class NotificationManager {
	private static final String TAG = "AAAANotifiManager";
	public static final String NTF_JOB_KEY = "ntfJob";
	public static final String NTF_JOB_INFO_KEY = "ntfJobInfo";

	@RequiresApi(api = Build.VERSION_CODES.M)
	public static void scheduleJob(Context context) {
		try {
			int jobId = 123456;
			ComponentName serviceComponet = new ComponentName(context, NotificattionJobService.class);
			JobInfo.Builder builder = new JobInfo.Builder(jobId, serviceComponet);
			//
			int minute = new Random().nextInt(1) + 2;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, minute);
			long delaytime = cal.getTimeInMillis() - System.currentTimeMillis();
			Log.d(TAG, "scheduleJob delayTime %s: " + delaytime);
			//Delay time to start run job
			builder.setMinimumLatency(delaytime);
			//Delay max time to start run job
			builder.setOverrideDeadline(delaytime + 30000);
			//
			JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
			if (jobScheduler != null) {
				if (isJobRunning(jobScheduler, jobId)) {
					jobScheduler.cancel(jobId);
					jobScheduler.schedule(builder.build());
				} else {
					jobScheduler.schedule(builder.build());
				}
			}
			Log.d(TAG, ">>>>>>>>>>>>jobId: " +jobId);
			scheduleNotifyJob(context);


		} catch (Exception e) {
			Log.e(TAG, "Error scheduleJob" + e);
		}

	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	private static void scheduleNotifyJob(Context context) {
		Notify notify = getNotification();
		if (notify == null) {
			Log.d(TAG, "notify is NULL");
			return;
		}
		JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
		if (!isCreateJob(jobScheduler, notify)) {
			Log.d(TAG, "isCreateJob is FALSE");
			return;
		}
		Log.d(TAG, "notify ....>>>>>>>>>>>>>>> "+notify);
		//Show notify after 1 minute
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE,1);
//		if (minute > 1) {
//			return;
//		}
		long delayMillis = 60 * 1000;
		Log.d(TAG, "Schedule Job for" + delayMillis);
		ComponentName serviceComponent = new ComponentName(context, NotificattionJobService.class);
		JobInfo.Builder builder = new JobInfo.Builder(Integer.valueOf(notify.getIdNotify()), serviceComponent);
		//Time delay to start job - time by sec
		builder.setMinimumLatency(delayMillis);
		// Max time delay to run job - time bt sec
		builder.setOverrideDeadline(delayMillis + (60000L));
		//
		PersistableBundle bundle = new PersistableBundle();
		bundle.putString(NTF_JOB_INFO_KEY, notify.toString());
		bundle.putBoolean(NTF_JOB_KEY, Boolean.TRUE);
		builder.setExtras(bundle);
		jobScheduler.schedule(builder.build());

	}

	public static Notify getNotification() {
		List<Notify> notifiesList = new ArrayList<>();
		notifiesList.add(new Notify("1",1,"Hello World"));
		notifiesList.add(new Notify("2",2, "Research Notify"));
//		notifiesList.add(new Notify("3","Notify", "Start project Notify"));
//		notifiesList.add(new Notify("4", "Notify","Update project Notify continue"));
//		notifiesList.add(new Notify("5", "Notify","Finish!"));
		if (!notifiesList.isEmpty()) {
			Collections.sort(notifiesList, new ComparatorNotify(true));
			return notifiesList.get(0);
		}

		return null;
	}

	public static class ComparatorNotify implements Comparator<Notify> {
		private boolean isASC = true;

		public ComparatorNotify(boolean isASC) {
			this.isASC = isASC;
		}

		public int compare(Notify r1, Notify r2) throws ClassCastException {
			if (isASC) {
				return Long.valueOf(r1.getIdNotify()).compareTo(Long.valueOf(r2.getIdNotify()));
			}
			return Long.valueOf(r2.getIdNotify()).compareTo(Long.valueOf(r1.getIdNotify()));
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	private static boolean isCreateJob(JobScheduler jobScheduler, Notify notify) {
		int jobId = Integer.valueOf(notify.getContentNotity());

		return true;
	}

	private static boolean isJobRunning(JobScheduler jobScheduler, int jobId) {
		//
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return jobScheduler.getPendingJob(jobId) != null;
		} else {
			for (JobInfo info : jobScheduler.getAllPendingJobs()) {
				if (info.getId() == jobId) {
					return true;
				}
			}
		}
		return false;
	}

	private static void canceJob(Context context, String jobId) {
		try {
			Log.d(TAG, "cancelJob jobId: " + jobId);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
				jobScheduler.cancel(Integer.valueOf(jobId));
			}

		} catch (Exception e) {
			Log.d(TAG, "Error cancel Job");
		}
	}

	private static void processAfterSent(Context context, String id) {
		Log.d(TAG, "processAfterSent" + id);
		canceJob(context, id);
		//reschedule job
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			scheduleJob(context);
		}
	}

	private static long getWattingMinute(String time) {
		long nextMinute = getMinute(Long.parseLong(time));
		if (nextMinute > 0) {
			return nextMinute;
		}
		return 0L;
	}

	private static long getMinute(long t1) {
		return ((t1 - (System.currentTimeMillis())) / 60000L);
	}


}

