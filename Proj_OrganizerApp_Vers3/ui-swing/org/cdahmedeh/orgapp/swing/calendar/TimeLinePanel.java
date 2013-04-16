package org.cdahmedeh.orgapp.swing.calendar;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.helpers.GraphicsHelper;

public class TimeLinePanel extends JPanel {

	private BigContainer bigContainer;

	/**
	 * Create the panel.
	 */
	public TimeLinePanel(BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		
		setPreferredSize(new Dimension(40, 100));
	}

	@Override
	protected void paintComponent(Graphics g) {
		GraphicsHelper.enableAntiAliasing(g);
		
		if (bigContainer == null) return;
		
		super.paintComponent(g);
		
		GridPainter.drawHours(g, this, bigContainer.getCurrentView());
	}
	
}
