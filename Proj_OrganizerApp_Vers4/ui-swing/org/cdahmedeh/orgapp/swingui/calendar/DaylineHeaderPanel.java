package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.joda.time.LocalDate;

public class DaylineHeaderPanel extends JPanel {

	private static final long serialVersionUID = 4294971143265192080L;

	private final int defaultMargin = 5;
	private final int defaultTextYPosition = 15;

	private LocalDate firstDay;
	private int numberOfDaysVisible;

	/**
	 * Create the panel.
	 */
	public DaylineHeaderPanel() {
		firstDay = new LocalDate(2013, 04, 21);
		numberOfDaysVisible = 7;
		
		setPreferredSize(new Dimension(50, 20));
		setBackground(new Color(245, 245, 245));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		for (int i = 0; i < numberOfDaysVisible; i++){
			g.setColor(new Color(200, 200, 200));	
			
			int lineXPosition = (int) ((this.getWidth()-1) * i/(double)numberOfDaysVisible);
			g.drawLine(lineXPosition, 0, lineXPosition, this.getHeight());
			
			ArrayDeque<String> formats = new ArrayDeque<>();
			formats.add("EEE d MMM");
			formats.add("EEE d");
			formats.add("EEE");
			formats.add("d");
			
			g.setColor(new Color(100, 100, 100));
			drawText(g, i, lineXPosition, formats);
		}
		
		g.setColor(new Color(200, 200, 200));	
		g.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight());
	}

	//See if the text fits, if it doesn't, try drawing something smaller.
	private void drawText(Graphics g, int i, int widthOfTextArea, ArrayDeque<String> formats) {
		String stringToPrint = firstDay.plusDays(i).toString(formats.pop());
		int stringToPrintWidth = g.getFontMetrics().stringWidth(stringToPrint);
		if (stringToPrintWidth < - defaultMargin*2 + (this.getWidth()-1)/(double)numberOfDaysVisible) {
			g.drawString(stringToPrint, widthOfTextArea+defaultMargin, defaultTextYPosition);
		} else {
			if (formats.isEmpty()) return;
			drawText(g, i, widthOfTextArea, formats);
		}
	}
	
	
}
