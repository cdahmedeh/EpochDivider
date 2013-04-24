package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;

public class SchedulerPanel extends JPanel {
	/**
	 * Create the panel.
	 */
	public SchedulerPanel() {
		setPreferredSize(new Dimension(50, 1000));
		setBackground(new Color(255, 255, 255));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		GridPainter.drawTimeLines(g, new Color(230,230,230), new Color(250,250,250), this.getWidth(), this.getHeight(), 24, 4);
		
		for (int i = 0; i < 7; i++){
			g.setColor(new Color(230, 230, 230));
			
			int lineXPosition = (int) ((this.getWidth()-1) * i/(double)7);
			g.drawLine(lineXPosition, 0, lineXPosition, this.getHeight());
			
		}
		
		g.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight());
	}
}
