package org.cdahmedeh.orgapp.misc;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.custom.SashForm;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class NewApp {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NewApp window = new NewApp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		GridLayout gl_shell = new GridLayout(1, false);
		gl_shell.marginWidth = 0;
		gl_shell.marginHeight = 0;
		shell.setLayout(gl_shell);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_btnNewButton.heightHint = 45;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setForeground(SWTResourceManager.getColor(0, 255, 127));
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnNewButton_1.setText("New Button");

	}
}
