package org.cdahmedeh.orgapp.ui.task;

import org.cdahmedeh.orgapp.types.category.Category;
import org.cdahmedeh.orgapp.types.category.CategoryContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.ui.components.DateEntryWidget;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.RowLayout;

public class TaskEditorDialog extends Dialog {

	protected Task result;
	protected Shell shell;
	private Text titleText;
	private Combo categoryCombo;
	private DateEntryWidget dueDateEntry;
	
	private CategoryContainer categoryContainer;

	public TaskEditorDialog(Shell parent, int style, Task task, CategoryContainer categoryContainer) {
		super(parent, style);
		setText("SWT Dialog");
		this.categoryContainer = categoryContainer;
		this.result = task;
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
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite fieldsComposite = new Composite(shell, SWT.NONE);
		fieldsComposite.setLayoutData(BorderLayout.CENTER);
		fieldsComposite.setLayout(new GridLayout(3, false));
		
		Label titleLabel = new Label(fieldsComposite, SWT.NONE);
		titleLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		titleLabel.setText("Task:");
		
		titleText = new Text(fieldsComposite, SWT.BORDER);
		titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label categoryLabel = new Label(fieldsComposite, SWT.NONE);
		categoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		categoryLabel.setText("Category:");
		
		categoryCombo = new Combo(fieldsComposite, SWT.BORDER);
		categoryCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button immutableButton = new Button(fieldsComposite, SWT.CHECK);
		immutableButton.setText("Immutable");
		
		Label seperator1 = new Label(fieldsComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		seperator1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label dueDateLabel = new Label(fieldsComposite, SWT.NONE);
		dueDateLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		dueDateLabel.setText("Due:");
		
		dueDateEntry = new DateEntryWidget(fieldsComposite, SWT.BORDER);
		dueDateEntry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Composite buttonsComposite = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayoutData(BorderLayout.SOUTH);
		buttonsComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button saveButton = new Button(buttonsComposite, SWT.NONE);
		saveButton.setText("Save");
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveFields();
				shell.close();
			}
		});
		
//		Button cancelButton = new Button(buttonsComposite, SWT.NONE);
//		cancelButton.setText("Cancel");
		
		loadFields();
	}
	
	public void loadFields(){
		titleText.setText(result.getTitle());
		for (Category category: categoryContainer.getAllCategories()) categoryCombo.add(category.getName());
		categoryCombo.setText(result.getCategory().getName());
		dueDateEntry.setDateTime(result.getDueDate());
	}
	
	public void saveFields(){
		result.setTitle(titleText.getText());
		result.setCategory(categoryContainer.getCategoryFromName(categoryCombo.getText()));
		result.setDueDate(dueDateEntry.getDateTime());
	}
}
