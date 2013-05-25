package org.cdahmedeh.orgapp.ui.editor;

import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.schedule.Task;
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

public class TaskEdit extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	final private Task task;
	private Label lblNewLabel;
	private Composite composite;
	private Text text_1;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Text text_2;
	private Label lblNewLabel_3;
	private Text text_3;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public TaskEdit(Shell parent, int style, Task task) {
		super(parent, style);
		setText("SWT Dialog");
		this.task = task;
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
		
		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setSize(22, 12);
		lblNewLabel.setText("Title");
		
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setSize(63, 22);
		text.setText(task.getTaskName());
		
		lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setText("Project");
		
		text_3 = new Text(composite, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setSize(69, 22);
		btnNewButton.setLayoutData(BorderLayout.SOUTH);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				task.setTaskName(text.getText());
//				task.setDescription(text_3.getText());
				shell.close();
				shell.dispose();
			}
		});
		btnNewButton.setText("OK");
	}

}
