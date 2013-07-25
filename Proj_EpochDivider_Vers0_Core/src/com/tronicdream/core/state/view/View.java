package com.tronicdream.core.state.view;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.tronicdream.core.tools.DateReference;

/**
 * The View represents a time window that is viewable in a Calendar. It is 
 * defined by the first day that is visible (startDate) and the last day that is
 * visible (lastDate). 
 * 
 * @author Ahmed El-Hajjar
 */
public class View {
	
	/* - Primary Fields - */
	
	private LocalDate startDate = DateReference.getSundayOfThisWeek();
	public LocalDate getStartDate() {return startDate;}
	public void setStartDate(LocalDate start) {this.startDate = start;}
	
	private LocalDate endDate = DateReference.getSaturdayOfThisWeek();
	public LocalDate getEndDate() {return endDate;}
	public void setEndDate(LocalDate end) {this.endDate = end;}

	
	/* - Reader Methods - */
	
	/**
	 * Calculates the number of days between the start and end date including
	 * the last 24 hours on the end date.
	 * 
	 * For example, between Jan 24 and Jan 26, there are 2+1 = 3 days.
	 * 
	 * @return Number of days between startDate and endDate. 
	 */
	public int getNumberOfDaysVisible(){
		return 1 + Days.daysBetween(startDate, endDate).getDays();
	}
}