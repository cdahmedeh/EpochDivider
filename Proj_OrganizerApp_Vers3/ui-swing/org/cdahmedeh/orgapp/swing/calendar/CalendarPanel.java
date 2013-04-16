package org.cdahmedeh.orgapp.swing.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.eclipse.swt.graphics.Rectangle;

public class CalendarPanel extends JPanel {
	private static final long serialVersionUID = -4698663372347441273L;
	
	private HashMap<TimeBlock, Task> timeBlockTaskMap = new HashMap<>();
	private HashMap<Rectangle, TimeBlock> rectangleTimeBlockMap = new HashMap<>();
	private BigContainer bigContainer;
	
	/**
	 * Create the panel.
	 */
	public CalendarPanel(BigContainer bigContainer) {
//		setBounds(0, 0, 800, 600);
		setPreferredSize(new Dimension(300, 800));
		
		this.bigContainer = bigContainer;
		this.setBackground(Color.WHITE);
		
		fillTimeBlockTaskMap();

	}
	
	public void fillTimeBlockTaskMap() {
		for (Task task: bigContainer.getTaskContainer().getAllTasks()){
			for (TimeBlock timeBlock: task.getAllTimeBlocks()){
				timeBlockTaskMap.put(timeBlock, task);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
        GridPainter.drawTimeGrid(g, this, bigContainer.getCurrentView());
        		
        rectangleTimeBlockMap.clear();
        
        for (Entry<TimeBlock, Task> entry : timeBlockTaskMap.entrySet()){
        	ArrayList<Rectangle> rectangles = TimeBlockPainter.draw(entry.getKey(), entry.getValue(), bigContainer.getCurrentView(), g, this);
        	for (Rectangle rectangle: rectangles){
        		rectangleTimeBlockMap.put(rectangle, entry.getKey());
        	}
		}
	}
	
}
