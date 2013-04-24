package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

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
		
		createToolbar();
		
		JPanel calendarHeader = new DaylineHeaderPanel();
		calendarPane.setColumnHeaderView(calendarHeader);
		
		JPanel timeLine = new TimelinePanel();
		timeLine.setPreferredSize(new Dimension(40, 1000));
		calendarPane.setRowHeaderView(timeLine);
		
		JPanel mainView = new SchedulerPanel();
		mainView.setPreferredSize(new Dimension(1, 1000));
		calendarPane.setViewportView(mainView);
	}

	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.NORTH);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		toolbar.add(horizontalGlue);
		
		JButton previousWeekButton = new JButton("Previous Week");
		previousWeekButton.setIcon(new ImageIcon(CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/previous.png")));
		toolbar.add(previousWeekButton);
		
		JButton nextWeekButton = new JButton("Next Week");
		nextWeekButton.setIcon(new ImageIcon(CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/next.png")));
		toolbar.add(nextWeekButton);
	}

}
