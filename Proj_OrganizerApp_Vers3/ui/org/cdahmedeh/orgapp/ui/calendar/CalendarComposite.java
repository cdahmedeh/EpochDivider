package org.cdahmedeh.orgapp.ui.calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

public class CalendarComposite extends Composite {
	@Override protected void checkSubclass() {}
	
	public CalendarComposite(Composite parent, int style) {
		super(parent, style);
		
		this.setLayout(new FillLayout());
		
		Canvas canvas = new Canvas(this, SWT.NONE);
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	}
}