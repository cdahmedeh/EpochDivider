package org.cdahmedeh.orgapp.swing.calendar;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;

import org.cdahmedeh.orgapp.containers.BigContainer;

public class BigCalendarPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public BigCalendarPanel(BigContainer bigContainer) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new CalendarPanel(bigContainer);
		add(panel, BorderLayout.CENTER);
		
		JScrollBar scrollBar = new JScrollBar();
		add(scrollBar, BorderLayout.EAST);
	}

}
