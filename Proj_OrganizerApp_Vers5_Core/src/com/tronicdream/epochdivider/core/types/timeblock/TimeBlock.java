package com.tronicdream.epochdivider.core.types.timeblock;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;

import com.tronicdream.epochdivider.core.types.task.Task;

/**
 * A {@link TimeBlock} is a time interval between two instants. The beginning
 * instant is part of the interval, but the end instant is excluded. 
 * 
 * @author Ahmed El-Hajjar
 */
public class TimeBlock {
	
	/* - Primary Fields - */
	
	private int id = -1;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private DateTime start = null;
	public DateTime getStart() {return start;}
	public void setStart(DateTime start) {this.start = start;}
	
	private DateTime end = null;
	public DateTime getEnd() {return end;}
	public void setEnd(DateTime end) {this.end = end;}

	
	/* - Relationships - */
	
	private Task owner;
	public Task getOwner() {return owner;}
	public void setOwner(Task owner) {this.owner = owner;}
	
	
	/* - Reader Methods - */

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
	
	/* - Modifier Methods - */
	
	/**
	 * Moves the TimeBlock to 'start' time. It is similar to setStart(..) except
	 * that the duration of the TimeBlock does not change. 
	 */
	public void moveStart(DateTime start) {
		Duration originalDuration = this.getDuration();
		this.setStart(start);
		this.setEnd(start.plus(originalDuration));
	}


}
