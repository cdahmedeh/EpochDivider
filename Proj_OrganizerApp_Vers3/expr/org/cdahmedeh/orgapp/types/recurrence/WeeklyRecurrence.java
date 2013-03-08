package org.cdahmedeh.orgapp.types.recurrence;

import java.util.ArrayList;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class WeeklyRecurrence extends Recurrence {
	public WeeklyRecurrence(LocalDate start) {
		super(start);
	}

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
	
	@Override
	public StringBuilder buildSpecificRule() {
		StringBuilder sb = new StringBuilder();
		sb.append("FREQ=WEEKLY;");
		
		if (!selectedDaysOfWeek.isEmpty()){
			sb.append("BYDAY=");
			if (selectedDaysOfWeek.contains(DateTimeConstants.MONDAY)) sb.append("MO,");
			if (selectedDaysOfWeek.contains(DateTimeConstants.TUESDAY)) sb.append("TU,");
			if (selectedDaysOfWeek.contains(DateTimeConstants.WEDNESDAY)) sb.append("WE,");
			if (selectedDaysOfWeek.contains(DateTimeConstants.THURSDAY)) sb.append("TH,");
			if (selectedDaysOfWeek.contains(DateTimeConstants.FRIDAY)) sb.append("FR,");
			if (selectedDaysOfWeek.contains(DateTimeConstants.SATURDAY)) sb.append("SA,");
			if (selectedDaysOfWeek.contains(DateTimeConstants.SUNDAY)) sb.append("SU,");
			sb.append(";");
		}
		
		return sb;
	}
}
