package org.cdahmedeh.orgapp.ui.task;
import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.task.Recurrence;
import org.cdahmedeh.orgapp.task.RecurringTask;
import org.cdahmedeh.orgapp.task.Task;
import org.cdahmedeh.orgapp.ui.components.DateEntryWidget;
import org.cdahmedeh.orgapp.ui.components.DurationEntryWidget;
import org.cdahmedeh.orgapp.ui.components.PriorityEntryWidget;
import org.cdahmedeh.orgapp.ui.recurrence.RecurrenceEditor;
import org.cdahmedeh.orgapp.ui.recurrence.RecurrenceModeDialog;
import org.cdahmedeh.orgapp.ui.recurrence.RecurrenceSaveMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

public class TaskEditor extends Dialog {

	protected Shell shell;
	
	private Task task;
	private Context rootContext;
	
	public TaskEditor(Shell parent, int style, Task task, Context contexts) {
		super(parent, style);
		setText("SWT Dialog");
		
		this.task = task;
		this.rootContext = contexts;
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
		
		Label taskNameLabel = new Label(fieldsComposite, SWT.NONE);
		taskNameLabel.setText("Task");
		
		final Text taskNameEdit = new Text(fieldsComposite, SWT.BORDER);
		taskNameEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		taskNameEdit.setText(task.getName());
		
		Label taskContextLabel = new Label(fieldsComposite, SWT.NONE);
		taskContextLabel.setText("Context");
		
		final Combo taskContextCombo = new Combo(fieldsComposite, SWT.NONE);
		taskContextCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		for (Context context: rootContext.getAllSubContexts()){
			taskContextCombo.add(context.toString());
		}
		if (task.getContext() != null){
			taskContextCombo.setText(task.getContext().toString());
		}
		
		Label dueDateLabel = new Label(fieldsComposite, SWT.NONE);
		dueDateLabel.setText("Due Date");
		
		final DateEntryWidget dueDateEntry = new DateEntryWidget(fieldsComposite, SWT.NONE);
		dueDateEntry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dueDateEntry.setDateTime(task.getDue());
		
		Label scheduledDateLabel = new Label(fieldsComposite, SWT.NONE);
		scheduledDateLabel.setText("Scheduled");
		
		final DateEntryWidget scheduledDateEntry = new DateEntryWidget(fieldsComposite, SWT.NONE);
		scheduledDateEntry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		scheduledDateEntry.setDateTime(task.getScheduled());
		
		Label declaredDateLabel = new Label(fieldsComposite, SWT.NONE);
		declaredDateLabel.setText("Declared");
		
		final DateEntryWidget declaredDateEntry = new DateEntryWidget(fieldsComposite, SWT.NONE);
		declaredDateEntry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		declaredDateEntry.setDateTime(task.getDeclared());
		
		Label durationLabel = new Label(fieldsComposite, SWT.NONE);
		durationLabel.setText("Duration");
		
		final DurationEntryWidget durationEntry = new DurationEntryWidget(fieldsComposite, SWT.NONE);
		durationEntry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		durationEntry.setDuration(task.getDuration());
		
		Label priorityLabel = new Label(fieldsComposite, SWT.NONE);
		priorityLabel.setText("Priority");
		
		final PriorityEntryWidget priorityEntry = new PriorityEntryWidget(fieldsComposite, SWT.NONE);
		priorityEntry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		priorityEntry.setPriority(task.getPriority());
				
		Label recLabel = new Label(fieldsComposite, SWT.NONE);
		recLabel.setText("Reccurence");
		
		Button recButton = new Button(fieldsComposite, SWT.NONE);
		recButton.setText("Enable Reccurence");
		recButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RecurrenceEditor recurrenceEditor = new RecurrenceEditor(shell, SWT.NONE, task.getRecurrence() == null ? new Recurrence() : task.getRecurrence());
				recurrenceEditor.open();
				task.setRecurrence(recurrenceEditor.getReccurence());
			}
		});
		
		Label completedLabel = new Label(fieldsComposite, SWT.NONE);
		completedLabel.setText("Completed");
		
		final Button completedCheckbox = new Button(fieldsComposite, SWT.CHECK);
		completedCheckbox.setSelection(task.isCompleted());
		
		Button okButton = new Button(shell, SWT.NONE);
		okButton.setLayoutData(BorderLayout.SOUTH);
		okButton.setText("OK");
		
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				task.setName(taskNameEdit.getText());
				task.setContext(Context.fromString(taskContextCombo.getText(), rootContext));
				task.setDue(dueDateEntry.getDateTime());
				task.setScheduled(scheduledDateEntry.getDateTime());
				task.setDeclared(declaredDateEntry.getDateTime());
				task.setDuration(durationEntry.getDuration());
				task.setPriority(priorityEntry.getPriority());
				task.setCompleted(completedCheckbox.getSelection());
				
				if (task instanceof RecurringTask){
					RecurrenceModeDialog dialog = new RecurrenceModeDialog(shell, SWT.NONE);
					RecurrenceSaveMode open = dialog.open();
					switch (open){
					case ALL:
						((RecurringTask)task).recurrencePropagateAll();
						break;
					case SINGLE:
						((RecurringTask)task).recurrenceMakeException(task.getScheduled().toLocalDate());
						break;
					case FORWARD:
						((RecurringTask)task).recurrencePropagateForward();
						break;
					}
				}
				
				shell.close();
			}
		});
	}

}
