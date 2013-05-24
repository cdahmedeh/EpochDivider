package org.cdahmedeh.orgapp.swingui.calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.calendar.scheduler.DateToPixels;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.experimental.alg.color.ImpvGreedyColoring;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.collect.ArrayListMultimap;

public class TimeBlockGenerator {
	public static ArrayList<RenderedTimeBlock> produceRenderedTimeBlocksForTask(Task task, TimeBlock timeBlock, DataContainer dataContainer, JPanel panel){
		ArrayList<RenderedTimeBlock> rtbs = new ArrayList<>();
		View view = dataContainer.getView();
		
		int panelWidth = panel.getWidth() - 1;
		int panelHeight = panel.getHeight() - 1;
		
		LocalTime midnight = new LocalTime(0, 0);
		LocalTime endOfDay = new LocalTime(23,59,59,999);
		
		LocalDate tBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate tEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();
		
		int daysSpanning = timeBlock.daysSpaning();
		
		//Create the rectangles dimensions that will be rendered for the TimeBlock.
		if (daysSpanning == 0){
			rtbs.add(new RenderedTimeBlock(
				DateToPixels.getHorizontalPositionFromDate(tBeginDate , panelWidth , view), 
				DateToPixels.getVerticalPositionFromTime(tBeginTime , panelHeight), 
				DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), panelWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate , panelWidth , view), 
				DateToPixels.getHeightFromInterval(tBeginTime, tEndTime, panelHeight, view)
				));
		} else {
			rtbs.add(new RenderedTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(tBeginTime, panelHeight), 
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), panelWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate , panelWidth , view), 
					DateToPixels.getHeightFromInterval(tBeginTime, endOfDay, panelHeight, view) + 1 //TODO: +1 temp
					));
			rtbs.add( new RenderedTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tEndDate, panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, panelHeight), 
					DateToPixels.getHorizontalPositionFromDate(tEndDate.plusDays(1), panelWidth , view) - DateToPixels.getHorizontalPositionFromDate(tEndDate, panelWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, tEndTime, panelHeight, view)
					));
		}
		
		if (daysSpanning > 1){
			for (int i=1; i<daysSpanning; i++){
			rtbs.add(new RenderedTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i) , panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, panelHeight), 
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i).plusDays(1), panelWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i) , panelWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, endOfDay, panelHeight, view) + 1 //TODO: +1 temp
					));
			}
		}
	
		//Assign the data (task and timeBlock reference) to the rectangles.
		for (RenderedTimeBlock r: rtbs){
			r.setTask(task);
			r.setTimeBlock(timeBlock);
		}
		
		return rtbs;
	}

	public static ArrayList<RenderedTimeBlock> processIntersections(DataContainer dataContainer, ArrayList<RenderedTimeBlock> renderedTimeBlocks) {
		SimpleGraph<RenderedTimeBlock, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		
		for (RenderedTimeBlock rtbFirst: renderedTimeBlocks) {
			g.addVertex(rtbFirst);
		}
		
		for (RenderedTimeBlock rtbFirst: renderedTimeBlocks) {
			for (RenderedTimeBlock rtbSecond: renderedTimeBlocks) {
				if (rtbFirst.intersects(rtbSecond.x+1, rtbSecond.y+1, rtbSecond.width-2, rtbSecond.height-2)) {
					if (rtbFirst != rtbSecond) {
						g.addEdge(rtbFirst, rtbSecond);
					}
				}
			}	
		}
		
		ConnectivityInspector<RenderedTimeBlock, DefaultEdge> ci = new ConnectivityInspector<>(g);
		List<Set<RenderedTimeBlock>> connectedSets = ci.connectedSets();

		ImpvGreedyColoring<RenderedTimeBlock, DefaultEdge> gc = new ImpvGreedyColoring<>(g);
		int[] colors = gc.colors();
		
		for (Set<RenderedTimeBlock> set: connectedSets) {
			if (set.size() <= 1) continue;
			
			int color = 0;
			for (RenderedTimeBlock rtb: set) {
				int i = renderedTimeBlocks.indexOf(rtb);
				color = Math.max(color, colors[i]);
			}
				
			for (RenderedTimeBlock rtb: set) {
				int i = renderedTimeBlocks.indexOf(rtb);
				rtb.width = rtb.width/color;
				rtb.x = rtb.x + rtb.width*(colors[i]-1);
			}
		}

		return renderedTimeBlocks;
	}

}
