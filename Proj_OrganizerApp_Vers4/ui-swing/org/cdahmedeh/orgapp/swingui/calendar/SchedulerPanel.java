package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;

import com.google.common.eventbus.EventBus;

public class SchedulerPanel extends CPanel {
	private static final long serialVersionUID = 3673536421097243610L;
	public SchedulerPanel(final DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	@Override protected Object getEventRecorder() {return new Object(){};}

	// -- Data --
	private boolean drawTasks = false;
	private ArrayList<RendereredTask> rendersTask = new ArrayList<>();
	
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(50, 1000));
		setBackground(new Color(255, 255, 255));
	}

	@Override
	protected void postWindowInit() {
		drawTasks = true;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		//Draw grid in the background
		GridPainter.drawTimeLines(g, this.getWidth(), this.getHeight(), new Color(230,230,230), new Color(245,245,245), 15, false);
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), new Color(230,230,230), dataContainer.getView(), false);
		
		//Draw the time-blocks for all tasks.
		//TODO: Optimization, draw only those within view.
		if (drawTasks){
			rendersTask.clear();
			for (Task task: dataContainer.getTasks()){
				for (TimeBlock timeBlock: task.getAllTimeBlocks())
					rendersTask.add(TimeBlockPainter.draw(g, task, timeBlock, dataContainer.getView(), this));
			}
		}
	}
}
