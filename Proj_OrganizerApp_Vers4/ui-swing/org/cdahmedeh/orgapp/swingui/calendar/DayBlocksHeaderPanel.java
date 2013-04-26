package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.types.container.DataContainer;

public class DayBlocksHeaderPanel extends JPanel {

	private static final long serialVersionUID = 4294971143265192080L;
	private DataContainer dataContainer;

	/**
	 * Create the panel.
	 */
	public DayBlocksHeaderPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		setPreferredSize(new Dimension(50, 30));
		setBackground(CalendarConstants.DAYBLOCKS_HEADER_BACKGROUND_COLOR);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), CalendarConstants.DAYBLOCKS_HEADER_GRID_COLOR, dataContainer.getView(), false);
	}

	
}
