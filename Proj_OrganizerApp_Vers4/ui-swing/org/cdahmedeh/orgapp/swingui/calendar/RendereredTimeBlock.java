package org.cdahmedeh.orgapp.swingui.calendar;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;

/**
 * Maps rectangle coordinates to a Task and a TimeBlock.
 *  
 * @author Ahmed El-Hajjar
 */
public class RendereredTimeBlock {
	public int x;
	public int y;
	public int width;
	public int height;

	public RendereredTimeBlock(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	private Task task;
	public Task getTask() {return task;}
	public void setTask(Task task) {this.task = task;}

	private TimeBlock timeBlock;
	public TimeBlock getTimeBlock() {return timeBlock;}
	public void setTimeBlock(TimeBlock timeBlock) {this.timeBlock = timeBlock;}
	
	//Is x,y within the rectangle?
	//TODO: Code is from SWT Rectangle.
	public boolean isWithin(int x, int y) {
		return (x >= this.x) && (y >= this.y) && x < (this.x + width) && y < (this.y + height);
	}
}
