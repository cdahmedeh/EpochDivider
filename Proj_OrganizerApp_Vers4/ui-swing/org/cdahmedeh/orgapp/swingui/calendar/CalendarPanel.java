package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;
import javax.swing.ScrollPaneConstants;

public class CalendarPanel extends JPanel {
	private static final long serialVersionUID = -4789321610128363432L;

	// - Data -
	private DataContainer dataContainer;
	
	// - EventBus -
	private EventBus eventBus;

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(new EventRecorder());
	}
	
	class EventRecorder{
	}
	
	/**
	 * Create the panel.
	 * @param dataContainer 
	 */
	public CalendarPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		setPreferredSize(new Dimension(600, 500));
		setLayout(new BorderLayout());
		
		JScrollPane calendarPane = new JScrollPane();
		calendarPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(calendarPane, BorderLayout.CENTER);
		
		JPanel calendarHeader = new JPanel();
		calendarHeader.setBackground(Color.GRAY);
		calendarHeader.setPreferredSize(new Dimension(1, 50));
		calendarPane.setColumnHeaderView(calendarHeader);
		
		JPanel timeLine = new JPanel();
		timeLine.setBackground(Color.GRAY);
		timeLine.setPreferredSize(new Dimension(50, 1));
		calendarPane.setRowHeaderView(timeLine);
		
		JPanel mainView = new JPanel();
		mainView.setPreferredSize(new Dimension(1, 1000));
		calendarPane.setViewportView(mainView);
	}

}
