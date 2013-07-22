package com.tronicdream.core.type.timeblock;

import org.joda.time.DateTime;

/**
 * A {@link TimeBlock} is a time interval between two instants. The beginning
 * instant is part of the interval, but the end instant is excluded. 
 * 
 * @author Ahmed El-Hajjar
 */
public class TimeBlock {
	
	/* - Constructs - */
	
	public TimeBlock(DateTime startTime, DateTime endTime) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}
	
	/* - Primary Fields - */

	private DateTime startTime = null;
	public DateTime getStartTime() {return startTime;}
	public void setStartTime(DateTime startTime) {this.startTime = startTime;}
	
	private DateTime endTime = null;
	public DateTime getEndTime() {return endTime;}
	public void setEndTime(DateTime endTime) {this.endTime = endTime;}
	
	
	/* - Relationships - */
	
	private Object owner = null;
	public Object getOwner() {return owner;}
	public void setOwner(Object owner) {this.owner = owner;}
}
