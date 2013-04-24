package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;

public class TimelinePanel extends JPanel {

	private static final long serialVersionUID = 4294971143265192080L;



	/**
	 * Create the panel.
	 */
	public TimelinePanel() {
		setPreferredSize(new Dimension(40, 1000));
		setBackground(new Color(245, 245, 245));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		GridPainter.drawTimeLines(g, new Color(200,200,200), new Color(220,220,220), this.getWidth(), this.getHeight(), 60, true);
	}
}
