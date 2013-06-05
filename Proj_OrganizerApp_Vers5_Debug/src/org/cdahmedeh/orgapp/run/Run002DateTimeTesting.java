package org.cdahmedeh.orgapp.run;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Run002DateTimeTesting {
	public static void main(String[] args) {
		System.out.println(Days.daysBetween(new DateTime(2013, 01, 01, 23, 59, 59).toDateMidnight(), new DateTime(2013, 01, 02, 00, 00, 01).toDateMidnight()));
	}
}
