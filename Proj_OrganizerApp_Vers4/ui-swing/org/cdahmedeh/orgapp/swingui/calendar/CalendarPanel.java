package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import org.cdahmedeh.orgapp.swingui.helpers.ToolbarHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.ContextsChangedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.joda.time.DateTimeConstants;

import com.google.common.eventbus.EventBus;
import com.jidesoft.swing.JideScrollPane;

public class CalendarPanel extends CPanel {
	private static final long serialVersionUID = -4789321610128363432L;
	public CalendarPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	@Override protected Object getEventRecorder() {return new Object(){};}
	
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(CalendarConstants.CALENDAR_DEFAULT_WIDTH, CalendarConstants.CALENDAR_DEFAULT_HEIGHT));
		setBorder(UIManager.getBorder("ScrollPane.border"));
		setLayout(new BorderLayout());
		
		createToolbar();
		createCalendarPane();		
	}

	@Override
	protected void postWindowInit() {
	}
	
	private void createCalendarPane() {
		//Setup scroll-pane
		JideScrollPane calendarPane = new JideScrollPane();
		calendarPane.setBorder(BorderFactory.createEmptyBorder());
		calendarPane.setBackground(CalendarConstants.CALENDAR_PANE_BACKGROUND_COLOR);
		calendarPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(calendarPane, BorderLayout.CENTER);
		
		//Days header
		DaylineHeaderPanel calendarHeader = new DaylineHeaderPanel(dataContainer, eventBus);
		calendarPane.setColumnHeaderView(calendarHeader);
		
		//Left time-line.
		TimelinePanel timeLine = new TimelinePanel();
		calendarPane.setRowHeaderView(timeLine);
		
		//Calendar with TimeBlocks
		SchedulerPanel mainView = new SchedulerPanel(dataContainer, eventBus);
		calendarPane.setViewportView(mainView);
	}

	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBackground(CalendarConstants.CALENDAR_PANE_BACKGROUND_COLOR);
		add(toolbar, BorderLayout.NORTH);
		
		ToolbarHelper.createToolbarHorizontalGlue(toolbar);
//		JToggleButton timeBotButton = ToolbarHelper.createToolbarToggleButton(toolbar, "Time Bot", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/timebot.gif"));
//		ToolbarHelper.createToolbarSeperator(toolbar);
//		JToggleButton weekButton = ToolbarHelper.createToolbarToggleButton(toolbar, "Week", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/week.gif"));
//		JToggleButton monthButton = ToolbarHelper.createToolbarToggleButton(toolbar, "Month", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/month.gif"));
//		JToggleButton statisticsButton = ToolbarHelper.createToolbarToggleButton(toolbar, "Statistics", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/statistic.gif"));
//		ToolbarHelper.createToolbarSeperator(toolbar);
		final JToggleButton dimPassedButton = ToolbarHelper.createToolbarToggleButton(toolbar, "Dim Past", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/dim_passed.gif"));
		dimPassedButton.setSelected(dataContainer.getDimPast());
		ToolbarHelper.createToolbarSeperator(toolbar);
		JButton previousWeekButton = ToolbarHelper.createToolbarButton(toolbar, "Previous Week", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/previous.gif"));
		previousWeekButton.setBackground(CalendarConstants.CALENDAR_PANE_BACKGROUND_COLOR);
		JButton nextWeekButton = ToolbarHelper.createToolbarButton(toolbar, "Next Week", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/next.gif"));
		nextWeekButton.setBackground(CalendarConstants.CALENDAR_PANE_BACKGROUND_COLOR);
		
		dimPassedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataContainer.setDimPast(dimPassedButton.isSelected());
				repaint();
			}
		});
		
		previousWeekButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				moveViewByDays(-DateTimeConstants.DAYS_PER_WEEK);
			}
		});
		
		nextWeekButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				moveViewByDays(DateTimeConstants.DAYS_PER_WEEK);
			}
		});
	}
	

	private void moveViewByDays(int days) {
		dataContainer.getView().moveAmountOfDays(days);
		repaint();
		eventBus.post(new ContextsChangedNotification());
	}
}
