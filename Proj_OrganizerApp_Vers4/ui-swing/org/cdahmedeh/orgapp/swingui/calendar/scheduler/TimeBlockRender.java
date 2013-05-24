package org.cdahmedeh.orgapp.swingui.calendar.scheduler;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.ReadableDuration;

public class TimeBlockRender {
	private int pWidth = 0;
	private int pHeight = 0;
	
	private View view;

	private TimeBlock timeBlock;
	public TimeBlock getTimeBlock() {return timeBlock;}
	
	private Task task;
	public Task getTask() {return task;}

	private ArrayList<Rectangle> rects = new ArrayList<>();
	private ReadableDuration timeClickedOffset;
	public ArrayList<Rectangle> getRects() {return rects;}

	public TimeBlockRender(Task task, TimeBlock timeBlock, View view, int width, int height) {
		this.task = task;
		this.timeBlock = timeBlock;
		this.view = view;
		this.pWidth = width;
		this.pHeight = height;
	}


	
	private TimeBlockClickLocation click = null;
	
	public boolean pointerWithin(int x, int y) {
		for (Rectangle rect : rects) {
			if (rect.isWithin(x, y)){
				if (y-rect.y < 5) {
					click = TimeBlockClickLocation.TOP;
				} else if (y-rect.y > rect.height-10) {
					click = TimeBlockClickLocation.BOTTOM;
				} else {
					click = TimeBlockClickLocation.MIDDLE;
				}
				return true;
			}
		}
		return false;
	}
	
	public void generateRectangles() {
		int panelWidth = pWidth - 1;
		int panelHeight = pHeight - 1;

		LocalTime midnight = new LocalTime(0, 0);
		LocalTime endOfDay = new LocalTime(23, 59, 59, 999);

		LocalDate tBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate tEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();

		int daysSpanning = timeBlock.daysSpaning();

		rects.clear();

		if (daysSpanning == 0) {
			rects.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(tBeginTime, panelHeight),
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), panelWidth, view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
					DateToPixels.getHeightFromInterval(tBeginTime, tEndTime, panelHeight, view)));
		} else {
			rects.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(tBeginTime, panelHeight),
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), panelWidth, view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
					DateToPixels.getHeightFromInterval(tBeginTime, endOfDay, panelHeight, view) + 1 // TODO: +1 temp
			));
			if (daysSpanning > 1) {
				for (int i = 1; i < daysSpanning; i++) {
					rects.add(new Rectangle(
							DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i), panelWidth, view), 
							DateToPixels.getVerticalPositionFromTime(midnight, panelHeight),
							DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i).plusDays(1), panelWidth, view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i), panelWidth,view), 
							DateToPixels.getHeightFromInterval(midnight, endOfDay, panelHeight, view) + 1 // TODO: +1 temp
					));
				}
			}
			rects.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(tEndDate, panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, panelHeight),
					DateToPixels.getHorizontalPositionFromDate(tEndDate.plusDays(1), panelWidth, view) - DateToPixels.getHorizontalPositionFromDate(tEndDate, panelWidth, view), 
					DateToPixels.getHeightFromInterval(midnight, tEndTime, panelHeight, view)));
		}
	}

	public TimeBlockClickLocation click() {
		return click;
	}

	public void setMoveOffset(int x, int y) {
		DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(x, y, pWidth-1, pHeight-1, view);
		timeClickedOffset = new Duration(timeBlock.getStart(), timeFromMouse);							
	}
	
	public void move(int x, int y){
		DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(x, y, pWidth-1, pHeight-1, view);
		timeBlock.moveStart(PixelsToDate.roundToMins(timeFromMouse.minus(timeClickedOffset) , 15));
	}
	
	public void resizeTop(int x, int y){
		DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(x, y, pWidth-1, pHeight-1, view);
		timeBlock.setStart(PixelsToDate.roundToMins(timeFromMouse, 15));
	}
	
	public void resizeBottom(int x, int y){
		DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(x, y, pWidth-1, pHeight-1, view);
		timeBlock.setEnd(PixelsToDate.roundToMins(timeFromMouse, 15));
	}
}
