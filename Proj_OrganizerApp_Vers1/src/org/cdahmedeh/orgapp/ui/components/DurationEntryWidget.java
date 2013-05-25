package org.cdahmedeh.orgapp.ui.components;

import org.cdahmedeh.orgapp.ui.UIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

public class DurationEntryWidget extends Composite {
	@Override protected void checkSubclass() {}
	
	private Spinner hourSpinner;
	
	public DurationEntryWidget(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());
		
		hourSpinner = new Spinner(this, SWT.BORDER);
		hourSpinner.setMaximum(60000);
		
		Label hourLabel = new Label(this, SWT.NONE);
		hourLabel.setText("minutes");
	}

	public Duration getDuration(){
		Duration duration = new Duration(0);
		duration = duration.plus(hourSpinner.getSelection() * DateTimeConstants.MILLIS_PER_MINUTE);
		return duration;
	}
	
	public void setDuration(Duration duration){
		if (duration != null){
			hourSpinner.setSelection((int) duration.getStandardMinutes());
		}
	}
}
