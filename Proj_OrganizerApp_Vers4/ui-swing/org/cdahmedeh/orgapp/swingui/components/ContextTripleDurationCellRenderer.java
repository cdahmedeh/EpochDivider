package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;

public class ContextTripleDurationCellRenderer extends TripleDurationCellRenderer {
	private static final long serialVersionUID = -8255299386801283428L;

	@Override
	public void paint(Graphics g) {
//		super.paint(g);
//		super.paintComponent(g);
		super.paint(g);
		
		double totalProgress = value.getEstimate().getStandardSeconds();
		double secondProgress = value.getTotalScheduled().getStandardSeconds();
		double firstProgress = value.getTotalPassed().getStandardSeconds();
		
		Color brighter = new Color(0, 0, 0, 20);
		
		g.setColor(brighter);
		g.fillRect(0, 0, (int)(this.getWidth()*(secondProgress/totalProgress)), this.getHeight());
		g.fillRect(0, 0, (int)(this.getWidth()*(firstProgress/totalProgress)), this.getHeight());
	}
}
