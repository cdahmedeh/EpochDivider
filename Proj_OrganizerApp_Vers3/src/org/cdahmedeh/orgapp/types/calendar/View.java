package org.cdahmedeh.orgapp.types.calendar;

import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * The View represents a time window that is viewable in a Calendar. It is 
 * defined by the first day that is visible (startDate) and the last day that is
 * visible (lastDate). 
 * 
 * @author cdahmedeh
 */
public class View {
	
	/* ---- Constructs ---- */
	
	public View(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	/* ---- Main Data ---- */
	
	private LocalDate startDate = null;
	public LocalDate getStartDate() {return startDate;}
	public void setStartDate(LocalDate start) {this.startDate = start;}
	
	private LocalDate endDate = null;
	public LocalDate getEndDate() {return endDate;}
	public void setEndDate(LocalDate end) {this.endDate = end;}
	
	/* ---- Compare Methods ---- */
	
	@Override
	public int hashCode() {
		return (startDate.toString() + endDate.toString()).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	
	
	/* ---- Reader Methods ---- */
	
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
	
	
	/* ---- Manipulate Methods ---- */
	
	/**
	 * Displaces the view 'n' days. If 'n' is positive, then it is forward.
	 * Otherwise, if 'n' is negative, then move 'n' days backwards.
	 * 
	 * For example, if 'n' is 5 days, then
	 * 		view before: 12 jan - 15 jan
	 *      view after:  17 jan - 20 jan 
	 * 
	 * @param n Number of days to move forward. Negative n moves backwards.
	 */
	public void moveAmountOfDays(int n){
		this.setStartDate(this.getStartDate().plusDays(n));
		this.setEndDate(this.getEndDate().plusDays(n));
	}

}