package org.cdahmedeh.orgapp.ui.components;

import org.cdahmedeh.orgapp.task.Priority;
import org.cdahmedeh.orgapp.ui.UIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

public class PriorityEntryWidget extends Composite {
	@Override protected void checkSubclass() {}
	
	private Scale scale;
	private Label scalePreview;
	
	public PriorityEntryWidget(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());
		
		scale = new Scale(this, SWT.HORIZONTAL);
		scale.setMinimum(0);
		scale.setMaximum(3);
		scale.setPageIncrement(1);
		
		scalePreview = new Label(this, SWT.NONE);
		scalePreview.setText((Priority.fromValue(scale.getSelection()).getName()));
		
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scalePreview.setText((Priority.fromValue(scale.getSelection()).getName()));
			}
		});
	}

	public Priority getPriority(){
		return Priority.fromValue(scale.getSelection());
	}
	
	public void setPriority(Priority priority){
		if (priority != null){
			scale.setSelection(priority.getValue());
			scalePreview.setText((Priority.fromValue(scale.getSelection()).getName()));
		}
	}
}
