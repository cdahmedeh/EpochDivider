package org.cdahmedeh.orgapp.swing.calendar;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
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

public class BigCalendarPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public BigCalendarPanel(BigContainer bigContainer) {
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(500, 500));
		
		
		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new MigLayout("gap rel 0, insets 0", "[]0[grow,fill]", "[]0[grow,fill]"));
		
		JPanel panel_1 = new DayLinePanel(bigContainer);
		mainPanel.add(panel_1, "cell 1 0,grow");
		
		final JPanel panel = new TimeLinePanel(bigContainer);
		mainPanel.add(panel, "cell 0 1,grow");
		
		final JPanel calendarPanel = new CalendarPanel(bigContainer);
		mainPanel.add(calendarPanel, "cell 1 1,alignx left,growy");
		
		final JScrollBar scrollBar = new JScrollBar();
		add(scrollBar, BorderLayout.EAST);
		
		
		final View currentView = bigContainer.getCurrentView();
		scrollBar.setValues(currentView.getFirstHour().getHourOfDay(), 1, 0, 24 - currentView.getNumberOfHoursVisible());
		
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int selection = scrollBar.getValue();
				currentView.setLastHour(new LocalTime(selection+currentView.getNumberOfHoursVisible(),59,59,999));
				currentView.setFirstHour(new LocalTime(selection, 0, 0));
				calendarPanel.repaint();
				panel.repaint();
			}
		});
		
		
	}

}
