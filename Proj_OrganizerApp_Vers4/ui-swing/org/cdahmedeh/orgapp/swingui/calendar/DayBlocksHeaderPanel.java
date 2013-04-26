package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;

public class DayBlocksHeaderPanel extends JPanel {

	private static final long serialVersionUID = 4294971143265192080L;

	private int numberOfDaysVisible;

	/**
	 * Create the panel.
	 */
	public DayBlocksHeaderPanel() {
		numberOfDaysVisible = 7;
		
		setPreferredSize(new Dimension(50, 30));
		setBackground(new Color(255, 255, 255));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		for (int i = 0; i < numberOfDaysVisible; i++){
			g.setColor(new Color(225, 225, 225));	
			
			int lineXPosition = (int) ((this.getWidth()-1) * i/(double)numberOfDaysVisible);
			g.drawLine(lineXPosition, 0, lineXPosition, this.getHeight());
		}
		
		g.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight());
		
		g.setColor(new Color(235, 235, 235));
		g.drawLine(0,0,this.getWidth(),0);
		g.drawLine(0,this.getHeight()-1,this.getWidth(),this.getHeight()-1);
	}

	
}
