package org.cdahmedeh.orgapp.ui.recurrence;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class RecurrenceModeDialog extends Dialog {

	protected RecurrenceSaveMode result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public RecurrenceModeDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public RecurrenceSaveMode open() {
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
		shell.setSize(200, 100);
		shell.setText(getText());

		shell.setLayout(new FillLayout(SWT.VERTICAL));
		
		Button saveAllButton = new Button(shell, SWT.NONE);
		saveAllButton.setText("Modify all");
		saveAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = RecurrenceSaveMode.ALL;
				shell.close();
			}
		});
		
		Button saveThisOneOnly = new Button(shell, SWT.NONE);
		saveThisOneOnly.setText("Modify this one only.");
		saveThisOneOnly.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = RecurrenceSaveMode.SINGLE;
				shell.close();
			}
		});
		
		Button saveFutureButton = new Button(shell, SWT.NONE);
		saveFutureButton.setText("Modify all in future.");
		saveFutureButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = RecurrenceSaveMode.FORWARD;
				shell.close();
			}
		});
	}
	
	

}
