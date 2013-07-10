package org.tronicsoft.epochdivider.core.type.timeblock;

import org.joda.time.DateTime;

/**
 * A {@link TimeBlock} represents an interval of time. The interval is 
 * registered through two points in time namely, the time which the interval
 * starts, and the one on which it ends. 
 * 
 * Is it of significance that the end time instant is not part of the interval. 
 * 
 * TODO: Handle situations of endTime before startTime and vice versa.
 * 
 * @author Ahmed El-Hajjar
 *
 */
public class TimeBlock {
	
	// =-- Constructs --= //
	
	public TimeBlock(DateTime startTime, DateTime endTime) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}
	
	
	// =-- Main Attributes --= //
	
	private DateTime startTime = null;
	
	public DateTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	
	
	private DateTime endTime = null;
	
	public DateTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
}
