package org.cdahmedeh.orgapp.runs;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class Run008_ViewHashCodeCompare {
	public static void main(String[] args) {
		View view1 =  new View(new LocalDate(2013,3,25), new LocalDate(2013,3,25).plusDays(6), new LocalTime(12, 0, 0), new LocalTime(23, 59, 59, 999));
		View view2 =  new View(new LocalDate(2013,3,25), new LocalDate(2013,3,25).plusDays(6), new LocalTime(12, 0, 0), new LocalTime(23, 59, 59, 999));

		System.out.println(view1.hashCode());
		System.out.println(view2.hashCode());
		
		System.out.println(view1.equals(view2));
	}
}
