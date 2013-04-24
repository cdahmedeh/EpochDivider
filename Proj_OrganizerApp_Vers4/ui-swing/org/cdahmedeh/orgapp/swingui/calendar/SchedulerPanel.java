package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class SchedulerPanel extends JPanel {
	private DataContainer dataContainer;

	/**
	 * Create the panel.
	 */
	public SchedulerPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		setPreferredSize(new Dimension(50, 1000));
		setBackground(new Color(255, 255, 255));
		
		fillTimeBlockTaskMap();

		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DateTime date = PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), getWidth()-1, getHeight()-1, view);
				System.out.println(date.toString("dd, MMM, YYYY @ HH:mm"));
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		GridPainter.drawTimeLines(g, new Color(230,230,230), new Color(245,245,245), this.getWidth(), this.getHeight(), 15, false);
		
		for (int i = 0; i < 7; i++){
			g.setColor(new Color(230, 230, 230));
			
			int lineXPosition = (int) ((this.getWidth()-1) * i/(double)7);
			g.drawLine(lineXPosition, 0, lineXPosition, this.getHeight());
			
		}
		
		g.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight());
		
		rectangleTimeBlockMap.clear();

        for (Entry<TimeBlock, Task> entry : timeBlockTaskMap.entrySet()){
        	ArrayList<Rectangle> rectangles = TimeBlockPainter.draw(entry.getKey(), entry.getValue(), view, g, this);
        	for (Rectangle rectangle: rectangles){
        		rectangleTimeBlockMap.put(rectangle, entry.getKey());
        	}
		}
	}
	
	public void fillTimeBlockTaskMap() {
		for (Task task: dataContainer.getTasks()){
			for (TimeBlock timeBlock: task.getAllTimeBlocks()){
				timeBlockTaskMap.put(timeBlock, task);
			}
		}
	}
	
	private HashMap<TimeBlock, Task> timeBlockTaskMap = new HashMap<>();
	private HashMap<Rectangle, TimeBlock> rectangleTimeBlockMap = new HashMap<>();
	private View 		view = new View(new LocalDate(2013, 04, 21), new LocalDate(2013, 04, 27));

}
