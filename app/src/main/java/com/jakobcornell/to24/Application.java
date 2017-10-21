package com.jakobcornell.to24;

import java.util.Calendar;
import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Application extends android.app.Application {
	private static final String NEXT_RUN_KEY = "NEXT_RUN";
	private static final String CANARY_KEY = "CANARY_KEY";

	private static final int MIN_DAYS = 3;
	private static final int DAY_RANGE = 3;

	private static final Random random = new Random();

	private static Context context;
	public static SharedPreferences preferences;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
	}

	public static void scheduleService() {
		PendingIntent toService = PendingIntent.getService(
			context,
			0,
			new Intent(context, Service.class),
			0
		);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		long now = Calendar.getInstance().getTimeInMillis();

		boolean scheduleRequired;
		long triggerMillis;
		if (preferences.contains(NEXT_RUN_KEY) && preferences.getLong(NEXT_RUN_KEY, 0l) > now + 1000l) {
			scheduleRequired = !preferences.contains(CANARY_KEY);
			triggerMillis = preferences.getLong(NEXT_RUN_KEY, 0l);
		} else {
			scheduleRequired = true;
			triggerMillis = getNextTriggerTime().getTimeInMillis();
		}

		if (scheduleRequired) {
			if (android.os.Build.VERSION.SDK_INT >= 19) {
				alarmManager.setWindow(
					AlarmManager.RTC_WAKEUP,
					triggerMillis,
					AlarmManager.INTERVAL_HOUR * 6,
					toService
				);
			} else {
				alarmManager.set(
					AlarmManager.RTC_WAKEUP,
					triggerMillis,
					toService
				);
			}

			SharedPreferences.Editor editor = preferences.edit();
			editor.putLong(NEXT_RUN_KEY, triggerMillis);
			editor.putBoolean(CANARY_KEY, true);
			editor.apply();
		}
	}

	public static void handleShutdown() {
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(CANARY_KEY);
		editor.apply();
	}

	public static Calendar getNextTriggerTime() {
		Calendar triggerTime = Calendar.getInstance();
		triggerTime.add(Calendar.DATE, MIN_DAYS + random.nextInt(DAY_RANGE));
		triggerTime.add(Calendar.HOUR, random.nextInt(24) - 12);
		return triggerTime;
	}
}
