package org.cdahmedeh.orgapp.types.recurrence;

import java.util.ArrayList;

import org.joda.time.DateTimeConstants;

public class WeeklyRecurrence extends Recurrence {
	private ArrayList<Integer> selectedDaysOfWeek = new ArrayList<>();
	public void setSelectedDaysOfWeek(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday){	
		selectedDaysOfWeek.clear();
		if (monday) selectedDaysOfWeek.add(DateTimeConstants.MONDAY);
		if (tuesday) selectedDaysOfWeek.add(DateTimeConstants.TUESDAY);
		if (wednesday) selectedDaysOfWeek.add(DateTimeConstants.WEDNESDAY);
		if (thursday) selectedDaysOfWeek.add(DateTimeConstants.THURSDAY);
		if (friday) selectedDaysOfWeek.add(DateTimeConstants.FRIDAY);
		if (saturday) selectedDaysOfWeek.add(DateTimeConstants.SATURDAY);
		if (sunday) selectedDaysOfWeek.add(DateTimeConstants.SUNDAY);
	}
}
