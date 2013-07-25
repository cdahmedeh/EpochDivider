package com.tronicdream.swingui.calendar;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.joda.time.LocalDate;

import com.tronicdream.core.container.DataContainer;
import com.tronicdream.core.state.view.View;

public class CalendarPanel extends JPanel {
	private static final long serialVersionUID = -4672583554192291773L;

	/**
	 * Create the panel.
	 */
	public CalendarPanel() {
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		DataContainer dataContainer = new DataContainer();
		View view = dataContainer.getView();
		
		// Paint days of the week
		LocalDate dayCounter = view.getStartDate();
		do {
			int xPosFromDate = DateToPixels.getHorizontalPositionFromDate(dayCounter, getWidth(), view);
			g.drawLine(xPosFromDate, 0, xPosFromDate, getHeight());
			dayCounter = dayCounter.plusDays(1);
		} while (!dayCounter.isAfter(view.getEndDate().plusDays(1)));
		
		// Paint hours of the day
		
	}
	
}
