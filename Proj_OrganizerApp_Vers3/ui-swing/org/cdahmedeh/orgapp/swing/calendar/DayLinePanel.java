package org.cdahmedeh.orgapp.swing.calendar;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.helpers.GraphicsHelper;

public class DayLinePanel extends JPanel {

	private BigContainer bigContainer;

	/**
	 * Create the panel.
	 */
	public DayLinePanel(BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		
		setPreferredSize(new Dimension(100, 20));
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		
		if (bigContainer == null) return;
		
		super.paintComponent(g);
		
		GraphicsHelper.enableAntiAliasing(g);
		
//		GridPainter.drawHours(g, this, bigContainer.getCurrentView());
		GridPainter.drawDays(g, this, bigContainer.getCurrentView());
	}
	
}
