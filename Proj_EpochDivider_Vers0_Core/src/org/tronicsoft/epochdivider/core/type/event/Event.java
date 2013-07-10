package org.tronicsoft.epochdivider.core.type.event;

import org.tronicsoft.epochdivider.core.type.timeblock.TimeBlock;

/**
 * An {@link Event} is a representation of a block of time such as an 
 * appointment or a meeting. 
 * 
 * @author Ahmed El-Hajjar
 * 
 */
public class Event {

	// =-- Main Attributes --= //

	private static int idCounter = 0;
	private int id = Event.idCounter++;
	public int getId() {return id;}
	
	private String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title == null ? "" : title;}
	
	private TimeBlock timeBlock = null;
	public TimeBlock getTimeBlock() {return timeBlock;}
	public void setTimeBlock(TimeBlock timeBlock) {this.timeBlock = timeBlock;}
}
