package com.wbapp.openproject;

import com.revmob.RevMob;

import android.app.Activity;
import android.util.Log;

public class Config {
	
	public static Long PROJECT_ID  = null;
	public static int  TASK_STATUS = 0;
	
	private static final int MAX_OKANE_CLICK_LIMIT = 7;

	private static int clickCount = 0;

	// ----------------------------- okane ------------------------------//

	public static void OKANE(Activity activity, RevMob revmob) {
		Config.clickCount++;
		Log.d("clickCount", "+++++++++clickCount++++++++++++" + Config.clickCount);
		if (Config.clickCount % Config.MAX_OKANE_CLICK_LIMIT == 0) {
			revmob.showFullscreen(activity);
			Config.clickCount = 0;
		}
	}

	// ----------------------------- okane ------------------------------//

	public static boolean DEBUG_MODE = true;

}
