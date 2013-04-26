package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Dimension;
import java.awt.Graphics;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;

public class DayBlocksHeaderPanel extends CPanel {
	private static final long serialVersionUID = -1931689507788390417L;
	public DayBlocksHeaderPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	@Override protected Object getEventRecorder() {return new Object(){};}
		
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(CalendarConstants.DAYBLOCKS_HEADER_DEFAULT_WIDTH, CalendarConstants.DAYBLOCKS_HEADER_DEFAULT_HEIGHT));
		setBackground(CalendarConstants.DAYBLOCKS_HEADER_BACKGROUND_COLOR);
	}

	@Override
	protected void postWindowInit() {}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		//Draw Grid
		GridPainter.drawDateLines(g, 
				this.getWidth(), 
				this.getHeight(), 
				CalendarConstants.DAYBLOCKS_HEADER_GRID_COLOR, 
				dataContainer.getView(), 
				false
		);
		
		//Draw a line on Top and Bottom of Panel
		g.drawLine(0, 0, this.getWidth(), 0);
		g.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
	}
}
