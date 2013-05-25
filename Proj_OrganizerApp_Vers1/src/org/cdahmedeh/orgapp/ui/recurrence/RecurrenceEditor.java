package org.cdahmedeh.orgapp.ui.recurrence;
import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.task.Recurrence;
import org.cdahmedeh.orgapp.task.RecurrenceFrequency;
import org.cdahmedeh.orgapp.task.Task;
import org.cdahmedeh.orgapp.ui.components.DateEntryWidget;
import org.cdahmedeh.orgapp.ui.components.DurationEntryWidget;
import org.cdahmedeh.orgapp.ui.components.PriorityEntryWidget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;
import org.joda.time.LocalTime;

public class RecurrenceEditor extends Dialog {

	protected Shell shell;
	
	private Recurrence reccurence;
	public Recurrence getReccurence() {
		return reccurence;
	}
	
	public RecurrenceEditor(Shell parent, int style, Recurrence reccurence) {
		super(parent, style);
		setText("SWT Dialog");
		
		this.reccurence = reccurence;
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
		
		Label typeLabel = new Label(fieldsComposite, SWT.NONE);
		typeLabel.setText("Reccurence Type");
		
		Composite typeComp = new Composite(fieldsComposite, SWT.NONE);
		typeComp.setLayout(new FillLayout());
		
		Button typeNoneBtn = new Button(typeComp, SWT.RADIO);
		typeNoneBtn.setText("NONE");
		
		Button typeEventBtn = new Button(typeComp, SWT.RADIO);
		typeEventBtn.setText("EVENT");
		
		Label freqLabel = new Label(fieldsComposite, SWT.NONE);
		freqLabel.setText("Reccurence Frequency");
		
		Composite freqComp = new Composite(fieldsComposite, SWT.NONE);
		freqComp.setLayout(new FillLayout());
		
		final Button freqDailyButton = new Button(freqComp, SWT.RADIO);
		freqDailyButton.setText("Daily");
		if (reccurence.getFreq() == RecurrenceFrequency.DAILY) freqDailyButton.setSelection(true);
		
		final Button freqWeeklyButton = new Button(freqComp, SWT.RADIO);
		freqWeeklyButton.setText("Weekly");
		if (reccurence.getFreq() == RecurrenceFrequency.WEEKLY) freqWeeklyButton.setSelection(true);
		
		final Button freqMonthlyButton = new Button(freqComp, SWT.RADIO);
		freqMonthlyButton.setText("Monthly");
		if (reccurence.getFreq() == RecurrenceFrequency.MONTHLY) freqMonthlyButton.setSelection(true);
		
		final Button freqYearlyButton = new Button(freqComp, SWT.RADIO);
		freqYearlyButton.setText("Yearly");
		if (reccurence.getFreq() == RecurrenceFrequency.YEARLY) freqYearlyButton.setSelection(true);
		
		Label multLabel = new Label(fieldsComposite, SWT.NONE);
		multLabel.setText("Reccurence Multipler");
		
		final Spinner multSpinner = new Spinner(fieldsComposite, SWT.BORDER);
		multSpinner.setSelection(reccurence.getMult());
		
		Label amntLabel = new Label(fieldsComposite, SWT.NONE);
		amntLabel.setText("Amount");
		
		final Spinner amountSpinner = new Spinner(fieldsComposite, SWT.BORDER);
		amountSpinner.setSelection(reccurence.getAmount());
		
		Label endLabel = new Label(fieldsComposite, SWT.NONE);
		endLabel.setText("Until");
		
		final DateEntryWidget endEntry = new DateEntryWidget(fieldsComposite, SWT.BORDER);
		endEntry.setDateTime(reccurence.getUntil() == null ? null : reccurence.getUntil().toDateTime(new LocalTime(0)));
		
		Button okButton = new Button(shell, SWT.NONE);
		okButton.setLayoutData(BorderLayout.SOUTH);
		okButton.setText("OK");
		
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (freqDailyButton.getSelection()){
					reccurence.setFreq(RecurrenceFrequency.DAILY);
				} else if (freqYearlyButton.getSelection()){
					reccurence.setFreq(RecurrenceFrequency.WEEKLY);
				}
				reccurence.setMult(multSpinner.getSelection());
				reccurence.setAmount(amountSpinner.getSelection());
				reccurence.setUntil(endEntry.getDateTime() == null ? null : endEntry.getDateTime().toLocalDate());
				shell.close();
			}
		});
	}

}
