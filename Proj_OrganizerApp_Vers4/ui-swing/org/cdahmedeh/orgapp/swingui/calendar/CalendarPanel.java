package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import org.cdahmedeh.orgapp.swingui.helpers.ToolbarHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
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
		calendarPane.setBackground(CalendarConstants.CALENDAR_PANE_BACKGROUND_COLOR);
		calendarPane.setBorder(UIManager.getBorder("ScrollPane.border"));
		calendarPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(calendarPane, BorderLayout.CENTER);
		
		//Days header
		DaylineHeaderPanel calendarHeader = new DaylineHeaderPanel(dataContainer, eventBus);
		calendarPane.setColumnHeaderView(calendarHeader);
		
		//Due dates header
		DayBlocksHeaderPanel calendarSubHeader = new DayBlocksHeaderPanel(dataContainer, eventBus);
		calendarPane.setSubColumnHeaderView(calendarSubHeader);
		
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
		add(toolbar, BorderLayout.NORTH);
		
		ToolbarHelper.createToolbarHorizontalGlue(toolbar);
		JButton previousWeekButton = ToolbarHelper.createToolbarButton(toolbar, "Previous Week", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/previous.gif"));
		JButton nextWeekButton = ToolbarHelper.createToolbarButton(toolbar, "Next Week", CalendarPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/next.gif"));
		
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
		eventBus.post(new RefreshContextListRequest());
	}
}
