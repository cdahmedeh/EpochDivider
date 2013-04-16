package org.cdahmedeh.orgapp.swing.calendar;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollBar;

import org.cdahmedeh.orgapp.containers.BigContainer;
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
		
		JPanel panel = new TimeLinePanel(bigContainer);
		mainPanel.add(panel, "cell 0 1,grow");
		
		JPanel calendarPanel = new CalendarPanel(bigContainer);
		mainPanel.add(calendarPanel, "cell 1 1,alignx left,growy");
		
		JScrollBar scrollBar = new JScrollBar();
		add(scrollBar, BorderLayout.EAST);
	}

}
