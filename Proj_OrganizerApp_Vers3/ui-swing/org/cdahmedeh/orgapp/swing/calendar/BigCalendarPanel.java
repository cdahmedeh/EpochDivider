package org.cdahmedeh.orgapp.swing.calendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ScrollBar;
import org.joda.time.LocalTime;

import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;

public class BigCalendarPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public BigCalendarPanel(BigContainer bigContainer) {
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(500, 500));
		
		
		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		mainPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new DayLinePanel(bigContainer);
		panel.add(panel_1);
		
//		JScrollBar scrollBar = new JScrollBar();
//		panel.add(scrollBar, BorderLayout.EAST);
		
		final JPanel timeLinePanel = new TimeLinePanel(bigContainer);
	
		final JPanel bigCalendarPanel = new JPanel();
		bigCalendarPanel.setLayout(new BorderLayout(0,0));
		
		final JPanel calendarPanel = new CalendarPanel(bigContainer);
		bigCalendarPanel.add(calendarPanel);
		bigCalendarPanel.add(timeLinePanel, BorderLayout.WEST);
		
		final JScrollPane calendarPane = new JScrollPane(bigCalendarPanel);
		calendarPane.setBorder(BorderFactory.createEmptyBorder());
		calendarPane.setViewportBorder(null);
		calendarPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		calendarPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		mainPanel.add(calendarPane);

		
		
	}

}
