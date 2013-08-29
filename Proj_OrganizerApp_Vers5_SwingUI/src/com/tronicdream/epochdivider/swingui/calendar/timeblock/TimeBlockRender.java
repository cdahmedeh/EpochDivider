package com.tronicdream.epochdivider.swingui.calendar.timeblock;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.ReadableDuration;

import com.tronicdream.epochdivider.core.types.task.Task;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlock;
import com.tronicdream.epochdivider.core.types.view.View;

public class TimeBlockRender {
	private int pWidth = 0;
	private int pHeight = 0;
	
	private View view;

	private TimeBlock timeBlock;
	public TimeBlock getTimeBlock() {return timeBlock;}
	
	private Task owner;
	public Task getOwner() {return owner;}

	private ArrayList<BRectangle> rects = new ArrayList<>();
	private ReadableDuration timeClickedOffset;
	public ArrayList<BRectangle> getRects() {return rects;}

	public TimeBlockRender(TimeBlock timeBlock, View view, int width, int height) {
		this.timeBlock = timeBlock;
		this.owner = timeBlock.getOwner();
		this.view = view;
		this.pWidth = width - 1;
		this.pHeight = height - 1;
	}

	TimeBlockClickLocation tbcl = TimeBlockClickLocation.NONE;
	
	public boolean pointerWithin(int x, int y) {
		for (BRectangle rect : rects) {
			if (rect.isWithin(x, y)){
				setMoveOffset(x, y);
				if (y-rect.y < 5) {
					this.tbcl = TimeBlockClickLocation.TOP;
				} else if (y-rect.y > rect.height-10) {
					this.tbcl = TimeBlockClickLocation.BOTTOM;
				} else {
					this.tbcl = TimeBlockClickLocation.MIDDLE;
				}
				return true;
			}
		}
		return false;
	}
	
	public void generateRectangles() {
		//Don't waste time generating rectangles, if it is not within the view
		//TODO: Premature optimization
		if (!view.getInterval().contains(timeBlock.getStart())) return;
		if (!view.getInterval().contains(timeBlock.getEnd())) return;
		
		LocalTime midnight = new LocalTime(0, 0);
		LocalTime endOfDay = new LocalTime(23, 59, 59, 999);

		LocalDate tBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate tEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();

		int daysSpanning = timeBlock.daysSpaning();

		rects.clear();

		if (daysSpanning == 0) {
			rects.add(DateToRectangles.rectangleForTimeBlockRight(pWidth, pHeight, tBeginDate, tBeginTime, tEndTime, view));
		} else {
			rects.add(DateToRectangles.rectangelForTimeBlockLeft(pWidth, pHeight, endOfDay, tBeginDate, tBeginTime, view));
			if (daysSpanning > 1) {
				for (int i = 1; i < daysSpanning; i++) {
					rects.add(DateToRectangles.rectangleForTimeBlockMiddle(pWidth, pHeight, midnight, endOfDay, tBeginDate, i, view));
				}
			}
			rects.add(DateToRectangles.rectangleForTimeBlockRight(pWidth, pHeight, tEndDate, midnight, tEndTime, view));
		}
	}

	public void forceMove() {
		this.tbcl = TimeBlockClickLocation.MIDDLE;
		timeClickedOffset = Duration.ZERO;
	}


	public void forceResize() {
		this.tbcl = TimeBlockClickLocation.BOTTOM;
		timeClickedOffset = Duration.ZERO;
	}
	
	private void setMoveOffset(int x, int y) {
		DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(x, y, pWidth, pHeight, view);
		timeClickedOffset = new Duration(timeBlock.getStart(), timeFromMouse);		
	}
	
	public void move(int x, int y){
		DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(x, y, pWidth, pHeight, view);
		if (tbcl == TimeBlockClickLocation.MIDDLE){
			timeBlock.moveStart(PixelsToDate.roundToMins(timeFromMouse.minus(timeClickedOffset) , 15));
		} else if (tbcl == TimeBlockClickLocation.TOP) {
			timeBlock.setStart(PixelsToDate.roundToMins(timeFromMouse, 15));
		} else if (tbcl == TimeBlockClickLocation.BOTTOM) {
			timeBlock.setEnd(PixelsToDate.roundToMins(timeFromMouse, 15));
		}
	}

}
