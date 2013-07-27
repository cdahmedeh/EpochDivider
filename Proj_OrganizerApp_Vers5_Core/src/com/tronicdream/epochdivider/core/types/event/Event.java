package com.tronicdream.epochdivider.core.types.event;

import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlock;

/**
 * An {@link Event} is a representation of a happening such as an appointment
 * or meeting.
 * 
 * @author Ahmed El-Hajjar
 */
public class Event {
	
	/* - Primary Fields - */
	
	private int id = -1;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	
	
	/* - Relationships - */
	
	private Context context = null;
	public Context getContext() {return context;}
	public void setContext(Context context) {this.context = context;}
	
	private TimeBlock timeBlock = null;
	public TimeBlock getTimeBlock() {return timeBlock;}
	public void setTimeBlock(TimeBlock timeBlock) {this.timeBlock = timeBlock;}
	
}
