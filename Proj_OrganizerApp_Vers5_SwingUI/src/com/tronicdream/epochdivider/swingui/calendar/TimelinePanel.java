package com.tronicdream.epochdivider.swingui.calendar;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.tronicdream.epochdivider.swingui.helpers.GraphicsHelper;

public class TimelinePanel extends JPanel {
	private static final long serialVersionUID = 4294971143265192080L;

	public TimelinePanel() {
		setPreferredSize(new Dimension(CalendarConstants.TIMELINE_DEFAULT_WIDTH, CalendarConstants.TIMELINE_DEFAULT_HEIGHT));
		setBackground(CalendarConstants.TIMELINE_BACKGROUND_COLOR);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		GraphicsHelper.enableDefaultAASettings(g);
		GridPainter.drawTimeLines(g, 
				this.getWidth(), 
				this.getHeight(), 
				CalendarConstants.TIMELINE_GRID_HOUR_COLOR, 
				CalendarConstants.TIMELINE_GRID_MINUTE_COLOR,
				CalendarConstants.TIMELINE_MINUTES_RESOLUTION, 
				true);
	}
}
