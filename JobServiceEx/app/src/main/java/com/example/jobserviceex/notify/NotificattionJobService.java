package com.example.jobserviceex.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.jobserviceex.MainActivity;
import com.example.jobserviceex.R;
import com.example.jobserviceex.model.Notify;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.jobserviceex.MainActivity.NOTIFICATION_CHANNEL_ID;
import static com.example.jobserviceex.common.Common.formatViewDate;
import static com.example.jobserviceex.notify.NotificationManager.NTF_JOB_INFO_KEY;
import static com.example.jobserviceex.notify.NotificationManager.NTF_JOB_KEY;
import static com.example.jobserviceex.notify.NotificationManager.getNotification;

@RequiresApi(api = Build.VERSION_CODES.M)
public class NotificattionJobService extends JobService {
	private static final String TAG = "AAAANotiJobService";
	public static final String NTF_KEY_DATE_EVEVT = "keyDateEvent";
	private boolean jobCancelled = false;
	@Override
	public boolean onStartJob(JobParameters params) {
		Log.d(TAG, "onStartJob" + params.getJobId());
		if (params.getExtras().getBoolean(NTF_JOB_KEY)) {
			String notify = params.getExtras().getString(NTF_JOB_INFO_KEY);
			new SendGetNotification(this, getApplicationContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, notify);
			Log.d(TAG, "SendGetNotification");
		} else {
			//reschedule job
			com.example.jobserviceex.notify.NotificationManager.scheduleJob(getApplicationContext());
			Log.d(TAG, "scheduleJob");
		}
		return false;
	}

	private void createNotification(Context context, Notify notify) {
		Calendar calendar = Calendar.getInstance();
		StringBuilder title = new StringBuilder();
		StringBuilder strBuilder = new StringBuilder();
		title.append(notify.getDescription());
		strBuilder.append(notify.getContentNotity());
		// set next minute of calender
		calendar.add(Calendar.MINUTE, 2);
		// Prepape intent which is triggered if the notification is selected
		Intent dayIntent = getIntentToOpenActivity(context);
		dayIntent.putExtra(NTF_KEY_DATE_EVEVT, calendar);
		PendingIntent pIntent = PendingIntent.getActivity(context, String.valueOf(System.currentTimeMillis()).hashCode(), dayIntent, 0);
		//Build notification
		Notification.Builder builder = new Notification.Builder(context).setContentTitle(title);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID).setContentTitle(title);
		}
		builder.setStyle(new Notification.BigTextStyle().bigText(strBuilder.toString()))
				.setContentIntent(pIntent)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), getLargeIcon()));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder.setSmallIcon(getSmallIcon());
		} else {
			builder.setSmallIcon(getLargeIcon());
		}
		Notification notification = builder.build();
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Log.d(TAG, "createNotification1111" + notification);
		// hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(String.valueOf(System.currentTimeMillis()).hashCode(), notification);
		Log.d(TAG, "createNotification2222" + notification);

		//

	}

	private class SendGetNotification extends AsyncTask<String, Integer, Notify> {
		private WeakReference<Context> weakReferenceContext;
		private NotificattionJobService alarmNotification;

		public SendGetNotification(NotificattionJobService alarmNotification, Context context) {
			weakReferenceContext = new WeakReference<>(context);
			this.alarmNotification = alarmNotification;
		}

		@Override
		protected Notify doInBackground(String... params) {
			try {
				Context context = weakReferenceContext.get();
				if (context != null) {
					String strNtf = params[0];
					List<Notify> notifiesList = new ArrayList<>();

				}
				//
				Notify notify = new Notify("1", 1, "Hello World");
				alarmNotification.createNotification(context,notify);
				Log.d(TAG, "Job createNotification");
				return notify;

			} catch (Exception e) {
				Log.d(TAG, "Failed to send notification");
			}
			return null;
		}
	}

	protected Intent getIntentToOpenActivity(Context context) {
		return new Intent(context, MainActivity.class);
	}
	protected int getLargeIcon() {
		return R.drawable.ic_notify;
	}

	protected int getSmallIcon() {
		return R.drawable.ic_notify;
	}

	@Override
	public boolean onStopJob(JobParameters params) {
		jobCancelled = true;
		Log.d(TAG, "onStopJob" + params.getJobId());
		return true;
	}
}
