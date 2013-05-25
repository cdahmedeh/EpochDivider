package org.cdahmedeh.orgapp.ui.editor;

import org.cdahmedeh.orgapp.schedule.Event;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.GridData;
import org.joda.time.DateTime;

public class EventEdit extends Dialog {

	protected Object result;
	protected Shell shell;
	final private Event event;
	private Composite composite;

	public EventEdit(Shell parent, int style, Event event) {
		super(parent, style);
		setText("SWT Dialog");
		this.event = event;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 159);
		shell.setText(getText());
		shell.setLayout(new BorderLayout(0, 0));
		
		composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		final Text title = createField("Title", event.getTitle());
		final Text startDate = createField("Start Date", event.getBegin().toString());
		final Text endDate = createField("End Date", event.getEnd().toString());
		
		Button save = new Button(shell, SWT.NONE);
		save.setLayoutData(BorderLayout.SOUTH);
		save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				event.setTitle(title.getText());
				event.setBegin(new DateTime(startDate.getText()));
				event.setEnd(new DateTime(endDate.getText()));
				
				shell.close();
				shell.dispose();
			}
		});
		save.setText("OK");
	}

	private Text createField(String fieldLabel, String value) {
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText(fieldLabel);
		
		Text text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setText(value);
		
		return text;
	}

}
