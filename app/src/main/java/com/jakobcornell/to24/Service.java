package com.jakobcornell.to24;

import android.content.Intent;
import android.provider.Settings.System;

public class Service extends android.app.Service {
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.putString(getContentResolver(), System.TIME_12_24, "24");
		Application.scheduleService();
		stopSelf();
		return android.app.Service.START_NOT_STICKY;
	}

	@Override
	public android.os.IBinder onBind(Intent intent) {
		return null;
	}
}
