package org.cdahmedeh.orgapp.ui.components;

import org.cdahmedeh.orgapp.ui.calendar.CalendarUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.joda.time.DateTime;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

public class DateEntryWidget extends Composite {
	@Override protected void checkSubclass() {}
	
	DateTime date = null;
	
	private Text dateField;
	private Label preview;
	
	public DateEntryWidget(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());
		
		dateField = new Text(this, SWT.BORDER);
		preview = new Label(this, SWT.NONE);		
		
		dateField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updatePreview();
			}
		});
	}

	private void updatePreview() {
		Span parsed = null;
		try {
			parsed = Chronic.parse(dateField.getText());
		} catch (RuntimeException e){
			parsed = null;
		}
		if (parsed != null) {
				date = new DateTime(parsed.getBeginCalendar());
				preview.setText(date.toString(CalendarUIConstants.DEFAULT_DATE_FORMAT));
		} else {
			preview.setText("");
		}
	}

	public DateTime getDateTime(){
		return date;
	}
	
	public void setDateTime(DateTime date){
		if (date != null){
			this.date = date;
			dateField.setText(date.toString(CalendarUIConstants.DEFAULT_DATE_FORMAT));			
		}
	}
}
