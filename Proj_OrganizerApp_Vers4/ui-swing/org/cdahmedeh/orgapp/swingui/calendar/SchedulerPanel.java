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
		view = dataContainer.getView();
		
		setPreferredSize(new Dimension(50, 1000));
		setBackground(new Color(255, 255, 255));
		
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
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), new Color(230,230,230), view);
		
		rendersTask.clear();
		for (Task task: dataContainer.getTasks()){
			for (TimeBlock timeBlock: task.getAllTimeBlocks())
			rendersTask.add(TimeBlockPainter.draw(g, task, timeBlock, view, this));
		}
	}
	
	private ArrayList<RendereredTask> rendersTask = new ArrayList<>();
	private View 		view = null;

}
