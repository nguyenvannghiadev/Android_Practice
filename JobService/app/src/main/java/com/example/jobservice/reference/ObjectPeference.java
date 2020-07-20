package com.example.jobservice.reference;

import android.content.Context;
import android.content.SharedPreferences;

public class ObjectPeference {
	private static final String Preferences_Name = "JobService_pref";
	private static ObjectPeference mInstance;
	private SharedPreferences sharedPreferences;

	private ObjectPeference(Context context) {
		sharedPreferences = context.getSharedPreferences(Preferences_Name, Context.MODE_PRIVATE);
	}

	public static ObjectPeference getInstance(Context context) {
		if (mInstance == null) {
			synchronized (ObjectPeference.class) {
				if (mInstance == null) {
					mInstance = new ObjectPeference(context);
				}
			}
		}
		return mInstance;
	}

	public void saveData(String key, Long value) {
		SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
		prefsEditor.putLong(key, value);
		prefsEditor.apply();
		prefsEditor.commit();
	}

	public long getData(String key, Long defaultValue) {
		if (sharedPreferences != null) {
			return sharedPreferences.getLong(key, defaultValue);
		}
		return 0l;
	}
}
