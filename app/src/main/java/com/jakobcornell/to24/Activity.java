package com.jakobcornell.to24;

import android.os.Bundle;
import android.content.ComponentName;
import android.content.pm.PackageManager;

public class Activity extends android.app.Activity {
	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);

		// disable self
		getPackageManager().setComponentEnabledSetting(
			new ComponentName(this, getClass()),
			PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
			PackageManager.DONT_KILL_APP
		);

		finish();
	}
}
