package org.cdahmedeh.orgapp.types.time;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

public class OverflowTimeBlock extends TimeBlock{
	private Duration duration;
	private LocalDate date;
	
	public OverflowTimeBlock(LocalDate date, Duration duration){
		this.date = date;
		this.duration = duration;
	}
	
	@Override
	public Duration getDuration() {
		return duration;
	}
	
	public LocalDate getDate() {
		return date;
	}
}
