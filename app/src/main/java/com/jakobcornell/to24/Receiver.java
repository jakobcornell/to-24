package com.jakobcornell.to24;

import android.content.Context;
import android.content.Intent;

public class Receiver extends android.content.BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			Application.scheduleService();
		} else if (action.equals(Intent.ACTION_SHUTDOWN)) {
			Application.handleShutdown();
		}
	}
}
