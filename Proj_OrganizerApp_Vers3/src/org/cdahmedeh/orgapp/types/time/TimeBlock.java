package org.cdahmedeh.orgapp.types.time;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class TimeBlock {
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
		
	private DateTime start = null;
	public void setStart(DateTime start) {this.start = start;}
	public DateTime getStart() {return start;}
	
	private DateTime end = null;
	public void setEnd(DateTime end) {this.end = end;}
	public DateTime getEnd() {return end;}
	
	public Duration getDuration() {return new Duration(start, end);}
}
