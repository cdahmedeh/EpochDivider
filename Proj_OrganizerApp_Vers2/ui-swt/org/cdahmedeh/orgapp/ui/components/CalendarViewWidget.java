package org.cdahmedeh.orgapp.ui.components;

import org.eclipse.swt.widgets.Composite;

public class CalendarViewWidget extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CalendarViewWidget(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
