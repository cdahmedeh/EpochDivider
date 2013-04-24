package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.cdahmedeh.orgapp.swingui.helpers.ToolbarHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;
import com.jidesoft.swing.JideScrollPane;

import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class CalendarPanel extends CPanel {
	private static final long serialVersionUID = -4789321610128363432L;
	public CalendarPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	
	@Override protected Object getEventRecorder() {return new Object(){};}
	
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(600, 500));
		setLayout(new BorderLayout());
		
		createToolbar();
		createCalendarPane();		
	}

	@Override
	protected void postWindowInit() {
	}
	
	private void createCalendarPane() {
		JideScrollPane calendarPane = new JideScrollPane();
		calendarPane.setBorder(new LineBorder(Color.BLACK));
		calendarPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(calendarPane, BorderLayout.CENTER);
		
		DaylineHeaderPanel calendarHeader = new DaylineHeaderPanel(dataContainer);
		calendarPane.setColumnHeaderView(calendarHeader);
		
		DayBlocksHeaderPanel calendarSubHeader = new DayBlocksHeaderPanel();
		calendarPane.setSubColumnHeaderView(calendarSubHeader);
		
		TimelinePanel timeLine = new TimelinePanel();
		timeLine.setPreferredSize(new Dimension(40, 1000));
		calendarPane.setRowHeaderView(timeLine);
		
		SchedulerPanel mainView = new SchedulerPanel(dataContainer);
		mainView.setPreferredSize(new Dimension(1, 1000));
		calendarPane.setViewportView(mainView);
	}

	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.NORTH);
		
		Component horizontalGlue = ToolbarHelper.createToolbarHorizontalGlue(toolbar);
		JButton previousWeekButton = ToolbarHelper.createToolbarButton(toolbar, "Previous Week", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/previous.png"));
		JButton nextWeekButton = ToolbarHelper.createToolbarButton(toolbar, "Next Week", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/next.png"));
	}
}
