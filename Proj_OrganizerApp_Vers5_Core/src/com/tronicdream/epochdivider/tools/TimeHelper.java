package com.tronicdream.epochdivider.tools;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class TimeHelper {
	public static boolean isAtMidnight(DateTime dateTime){
		return dateTime.toLocalTime().isEqual(LocalTime.MIDNIGHT);
	}
}
