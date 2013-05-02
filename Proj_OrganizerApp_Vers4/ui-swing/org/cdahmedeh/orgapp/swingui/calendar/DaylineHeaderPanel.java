package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Dimension;
import java.awt.Graphics;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;

public class DaylineHeaderPanel extends CPanel {
	private static final long serialVersionUID = 8555200615702209829L;
	public DaylineHeaderPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	@Override protected Object getEventRecorder() {return new Object(){};}
		
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(CalendarConstants.DAYLINE_HEADER_DEFAULT_WIDTH, CalendarConstants.DAYLINE_HEADER_DEFAULT_HEIGHT));
		setBackground(CalendarConstants.DAYLINE_HEADER_BACKGROUND_COLOR);
	}

	@Override
	protected void postWindowInit() {}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		g.setColor(CalendarConstants.DAYLINE_GRID_COLOR);
		g.drawLine(0, 0, this.getWidth(), 0);
		
		GridPainter.drawDateLines(
				g, 
				this.getWidth(), 
				this.getHeight(), 
				CalendarConstants.DAYLINE_GRID_COLOR, 
				dataContainer.getView(), 
				true
		);
	}

}
