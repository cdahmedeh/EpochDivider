package org.cdahmedeh.orgapp.types.time;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;

public class TimeBlock {
	
	/* ---- Constructs ---- */

	public TimeBlock(DateTime start, DateTime end) {
		this.start = start;
		this.end = end;
	}

	public TimeBlock(){this(DateTime.now(), TimeConstants.DEFAULT_DURATION);}
	public TimeBlock(DateTime start){this(start, TimeConstants.DEFAULT_DURATION);}
	public TimeBlock(DateTime start, Duration duration){
		this.start = start;
		this.end = start.plus(duration);
	}

	
	/* ---- Main Data ---- */
	
	private DateTime start = null;
	public void setStart(DateTime start) {this.start = start;}
	public DateTime getStart() {return start;}
	
	private DateTime end = null;
	public void setEnd(DateTime end) {this.end = end;}
	public DateTime getEnd() {return end;}

	
	/* ---- Reader Methods ---- */

	/**
	 * Get duration of TimeBlock.
	 * 
	 * @return Duration of TimeBlock.
	 */
	public Duration getDuration() {
		return new Duration(start, end);
	}

	/**
	 * Gives the number of days that are covered by the TimeBlock.
	 * 
	 * For example, a TimeBlock that starts on Jan 2 at 23:59 and ends on Jan 3
	 * 00:01 spans 2 days even though it is less than 24 hours.
	 * 
	 * @return Number of days spanned by TimeBlock.
	 */
	public int daysSpaning() {
		return Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays();
	}
}
