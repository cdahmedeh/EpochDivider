package org.cdahmedeh.orgapp.swingui.calendar;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;

public class RendereredTask {
//	private ArrayList<Rectangle> rectangles = null; 
//	public void setRectangles(ArrayList<Rectangle> rectangles) {this.rectangles = rectangles;}
//	public ArrayList<Rectangle> getRectangles() {return rectangles;}
	
	private Rectangle rectangle = null;
	public Rectangle getRectangle() {return rectangle;}
	public void setRectangle(Rectangle rectangle) {this.rectangle = rectangle;}
	
	private Task task;
	public Task getTask() {return task;}
	public void setTask(Task task) {this.task = task;}

	private TimeBlock timeBlock;
	public TimeBlock getTimeBlock() {return timeBlock;}
	public void setTimeBlock(TimeBlock timeBlock) {this.timeBlock = timeBlock;}
	
	public boolean clickedWithin(int x, int y) {
		if (rectangle.contains(x, y)) return true;
		return false;
	}
}
