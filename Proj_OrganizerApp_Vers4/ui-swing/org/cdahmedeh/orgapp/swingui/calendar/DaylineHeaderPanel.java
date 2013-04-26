package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.types.container.DataContainer;

public class DaylineHeaderPanel extends JPanel {
	private static final long serialVersionUID = 4294971143265192080L;
	private DataContainer dataContainer;

	public DaylineHeaderPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		setPreferredSize(new Dimension(50, 20));
		setBackground(new Color(245, 245, 245));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), CalendarConstants.DAYLINE_GRID_COLOR, dataContainer.getView(), true);
	}
}
