package org.cdahmedeh.orgapp.ui.context;
import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.ui.components.DurationEntryWidget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.GridData;


public class ContextEditor extends Dialog {

	protected Shell shell;
	
	private Context context;

	public ContextEditor(Shell parent, int style, Context context) {
		super(parent, style);
		setText("SWT Dialog");
		
		this.context = context;
	}

	public void open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setText(getText());

		shell.setLayout(new BorderLayout());

		Composite fieldsComposite = new Composite(shell, SWT.NONE);
		
		fieldsComposite.setLayout(new GridLayout(2, false));
		
		Label contextNameLabel = new Label(fieldsComposite, SWT.NONE);
		contextNameLabel.setText("Context");
		
		final Text contextNameEdit = new Text(fieldsComposite, SWT.BORDER);
		contextNameEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contextNameEdit.setText(context.getName());
		
		Label contextGoalLabel = new Label(fieldsComposite, SWT.NONE);
		contextGoalLabel.setText("Goal");
		
		final DurationEntryWidget durationEntryWidget = new DurationEntryWidget(fieldsComposite, SWT.NONE);
		durationEntryWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		durationEntryWidget.setDuration(context.getGoal());
		
		Button okButton = new Button(shell, SWT.NONE);
		okButton.setLayoutData(BorderLayout.SOUTH);
		okButton.setText("OK");
		
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				context.setName(contextNameEdit.getText());
				context.setGoal(durationEntryWidget.getDuration());
				shell.close();
			}
		});
	}

}
