package org.cdahmedeh.orgapp.runs;

import org.cdahmedeh.orgapp.types.recurrence.DailyRecurrence;
import org.cdahmedeh.orgapp.types.recurrence.Recurrence;
import org.cdahmedeh.orgapp.types.recurrence.WeeklyRecurrence;
import org.joda.time.LocalDate;

import com.google.ical.compat.jodatime.LocalDateIteratorFactory;

/**
 * Testing own recurrence types.
 * 
 * @author cdahmedeh
 */
public class Run005_RecurrenceTypeTest {
	public static void main(String[] args) throws Exception {
		LocalDate start = new LocalDate(2013, 01, 01);
		 
		DailyRecurrence rec = new DailyRecurrence(start);
		rec.setAmount(10);
//		rec.setEnd(new LocalDate(2013, 10, 10));
		
		for (LocalDate date: rec.generateRecurrenceDateIterable()) System.out.println(date);
		
		System.out.println(" === ");
		
		LocalDate otherstart = new LocalDate(2013, 01, 01);
		 
		WeeklyRecurrence wrec = new WeeklyRecurrence(otherstart);
		wrec.setSelectedDaysOfWeek(true, true, false, false, false, false, true);
		wrec.setAmount(10);
//		rec.setEnd(new LocalDate(2013, 10, 10));
		
		for (LocalDate date: wrec.generateRecurrenceDateIterable()) System.out.println(date.toString("E"));
	}
}
