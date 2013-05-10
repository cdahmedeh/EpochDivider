package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;

public class ContextTripleDurationCellRenderer extends TripleDurationCellRenderer {
	private static final long serialVersionUID = -8255299386801283428L;

	@Override
	public void paint(Graphics g) {
//		super.paint(g);
		super.paintComponent(g);
//		super.paint(g);
//		super.setBorder(BorderFactory.createEmptyBorder());
		
		double totalProgress = value.getEstimate().getStandardSeconds();
		double secondProgress = value.getTotalScheduled().getStandardSeconds();
		double firstProgress = value.getTotalPassed().getStandardSeconds();
		
		Color light = new Color(0, 0, 0, 5);
		Color brighter = new Color(0, 0, 0, 20);
		
		g.setColor(light);
		g.fillRoundRect(2, 2, this.getWidth()-4, this.getHeight()-4, 3, 3);
		g.setColor(brighter);
		g.fillRoundRect(2, 2, (int)((this.getWidth()-4)*(secondProgress/totalProgress)), this.getHeight()-4, 3, 3);
		g.fillRoundRect(2, 2, (int)((this.getWidth()-4)*(firstProgress/totalProgress)), this.getHeight()-4, 3 ,3);
	}
}
