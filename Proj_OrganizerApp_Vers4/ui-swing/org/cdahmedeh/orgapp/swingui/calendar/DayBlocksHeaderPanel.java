package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.types.container.DataContainer;

public class DayBlocksHeaderPanel extends JPanel {
	private static final long serialVersionUID = 4294971143265192080L;
	private DataContainer dataContainer;

	public DayBlocksHeaderPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		setPreferredSize(new Dimension(CalendarConstants.DAYBLOCKS_HEADER_DEFAULT_WIDTH, CalendarConstants.DAYBLOCKS_HEADER_DEFAULT_HEIGHT));
		setBackground(CalendarConstants.DAYBLOCKS_HEADER_BACKGROUND_COLOR);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		//Draw Grid
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), CalendarConstants.DAYBLOCKS_HEADER_GRID_COLOR, dataContainer.getView(), false);
		
		//Draw a line on Top and Bottom of Panel
		g.drawLine(0, 0, this.getWidth(), 0);
		g.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
	}

	
}
