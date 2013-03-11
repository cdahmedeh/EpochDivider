package org.cdahmedeh.orgapp.ui.task;

import org.cdahmedeh.orgapp.types.task.Task;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TaskEditorDialog extends Dialog {

	protected Task result;
	protected Shell shell;

	public TaskEditorDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	public Task open() {
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

	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setText(getText());
	}
}
