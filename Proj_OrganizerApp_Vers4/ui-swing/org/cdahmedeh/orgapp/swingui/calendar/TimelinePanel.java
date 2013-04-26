package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;

public class TimelinePanel extends JPanel {
	private static final long serialVersionUID = 4294971143265192080L;

	public TimelinePanel() {
		setPreferredSize(new Dimension(CalendarConstants.TIMELINE_DEFAULT_WIDTH, CalendarConstants.TIMELINE_DEFAULT_HEIGHT));
		setBackground(CalendarConstants.TIMELINE_BACKGROUND_COLOR);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
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
