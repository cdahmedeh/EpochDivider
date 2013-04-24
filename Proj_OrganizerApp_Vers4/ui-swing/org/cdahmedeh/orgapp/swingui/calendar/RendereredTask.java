package org.cdahmedeh.orgapp.swingui.calendar;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;

public class RendereredTask {
	private ArrayList<Rectangle> rectangles = null; 
	public void setRectangles(ArrayList<Rectangle> rectangles) {this.rectangles = rectangles;}
	public ArrayList<Rectangle> getRectangles() {return rectangles;}
	
	private Task task;
	public Task getTask() {return task;}
	public void setTask(Task task) {this.task = task;}

	private TimeBlock timeBlock;
	public TimeBlock getTimeBlock() {return timeBlock;}
	public void setTimeBlock(TimeBlock timeBlock) {this.timeBlock = timeBlock;}
}
