package org.cdahmedeh.orgapp.misc;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class VeryNewApp {

	protected Shell shell;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			VeryNewApp window = new VeryNewApp();
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
		shell.setSize(800, 600);
		shell.setText("SWT Application");
		shell.setLayout(new BorderLayout(0, 0));
		
		SashForm sashForm = new SashForm(shell, SWT.SMOOTH);
		sashForm.setLayoutData(BorderLayout.CENTER);
		
		Tree tree = new Tree(sashForm, SWT.BORDER);
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		
		TreeColumn trclmnNewColumn = new TreeColumn(tree, SWT.RIGHT);
		trclmnNewColumn.setWidth(144);
		trclmnNewColumn.setText("Context");
		
		TreeColumn trclmnNewColumn_1 = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn_1.setResizable(false);
		trclmnNewColumn_1.setWidth(35);
		trclmnNewColumn_1.setText("Week");
		
		TreeColumn trclmnNewColumn_2 = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn_2.setResizable(false);
		trclmnNewColumn_2.setWidth(35);
		trclmnNewColumn_2.setText("Total");
		
		TreeItem trtmNewTreeitem22 = new TreeItem(tree, SWT.NONE);
		trtmNewTreeitem22.setText(new String[] {"Unsorted", "12", "15"});
		
		TreeItem trtmNewTreeitem322 = new TreeItem(tree, SWT.NONE);
		trtmNewTreeitem322.setText(new String[] {"Interruptions", "0", "0"});
		
		TreeItem trtmNewTreeitem_4 = new TreeItem(tree, SWT.NONE);
		trtmNewTreeitem_4.setText(new String[] {"All", "150", "-"});
		
		TreeItem trtmNewTreeitem = new TreeItem(tree, SWT.NONE);
		trtmNewTreeitem.setText(new String[] {"Essentials", "52.5", "-"});
		
		TreeItem treeItem = new TreeItem(trtmNewTreeitem, 0);
		treeItem.setText(new String[] {"Sleep", "49", "-"});
		
		TreeItem treeItem_1 = new TreeItem(trtmNewTreeitem, 0);
		treeItem_1.setText(new String[] {"Faith", "3.5", "-"});
		trtmNewTreeitem.setExpanded(true);
		
		TreeItem trtmNewTreeitem_1 = new TreeItem(tree, SWT.NONE);
		trtmNewTreeitem_1.setText(new String[] {"University", "21", "147"});
		
		TreeItem trtmNewTreeitem_2 = new TreeItem(trtmNewTreeitem_1, SWT.NONE);
		trtmNewTreeitem_2.setText(new String[] {"Course", "21", "147"});
		trtmNewTreeitem_1.setExpanded(true);
		
		TreeItem treeItem_2 = new TreeItem(tree, 0);
		treeItem_2.setText(new String[] {"Project", "26", "106"});
		
		TreeItem treeItem_5 = new TreeItem(treeItem_2, 0);
		treeItem_5.setText(new String[] {"Islam Web Portal", "13", "15"});
		
		TreeItem treeItem_3 = new TreeItem(treeItem_2, 0);
		treeItem_3.setText(new String[] {"OrbitHub", "18", "36"});
		
		TreeItem treeItem_4 = new TreeItem(treeItem_2, 0);
		treeItem_4.setText(new String[] {"Nature Sound", "12", "13"});
		treeItem_2.setExpanded(true);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new BorderLayout(0, 0));
		
		Tree tree_1 = new Tree(composite, SWT.BORDER | SWT.CHECK);
		tree_1.setHeaderVisible(true);
		
		TreeColumn trclmnOn = new TreeColumn(tree_1, SWT.NONE);
		trclmnOn.setWidth(50);
		trclmnOn.setText("On");
		
		TreeColumn trclmnNewColumn_3 = new TreeColumn(tree_1, SWT.NONE);
		trclmnNewColumn_3.setWidth(150);
		trclmnNewColumn_3.setText("Task");
		
		TreeColumn trclmnNewColumn_4 = new TreeColumn(tree_1, SWT.NONE);
		trclmnNewColumn_4.setWidth(125);
		trclmnNewColumn_4.setText("Context");
		
		TreeColumn trclmnDone = new TreeColumn(tree_1, SWT.NONE);
		trclmnDone.setWidth(40);
		trclmnDone.setText("Done");
		
		TreeColumn trclmnDuration = new TreeColumn(tree_1, SWT.NONE);
		trclmnDuration.setWidth(50);
		trclmnDuration.setText("Duration");
		
		TreeColumn trclmnNewColumn_5 = new TreeColumn(tree_1, SWT.NONE);
		trclmnNewColumn_5.setWidth(50);
		trclmnNewColumn_5.setText("Due");

		TreeColumn trclmnScheduled = new TreeColumn(tree_1, SWT.NONE);
		trclmnScheduled.setWidth(50);
		trclmnScheduled.setText("Scheduled");
		
		TreeItem trtmNewTreeitem_3 = new TreeItem(tree_1, SWT.NONE);
		trtmNewTreeitem_3.setBackground(SWTResourceManager.getColor(176, 224, 230));
		trtmNewTreeitem_3.setText(new String[] {"✓ ", "Final Report", "Project > OrbitHub", "2 hr", "19 hr", "29 mar", "today"});
		
		TreeItem treeItem_6 = new TreeItem(tree_1, 0);
		treeItem_6.setText(new String[] {" ", "Server Testing", "Project > OrbitHub", "0 hr", "2 hr", "3 april"});
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.SOUTH);
		composite_1.setLayout(new BorderLayout(0, 0));
		
		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setImage(SWTResourceManager.getImage("/usr/share/icons/gnome/16x16/actions/add.png"));
		btnNewButton.setLayoutData(BorderLayout.EAST);
		btnNewButton.setText("Add");
		
		text = new Text(composite_1, SWT.BORDER);
		sashForm.setWeights(new int[] {2, 5});
		
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.NORTH);
		composite_2.setLayout(new BorderLayout(0, 0));
		
		ToolBar toolBar = new ToolBar(composite_2, SWT.FLAT | SWT.RIGHT);
		
		ToolItem tltmNewItem_4 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_4.setImage(SWTResourceManager.getImage("/usr/share/icons/gnome/16x16/actions/gnome-run.png"));
		tltmNewItem_4.setText("Activate");
		
		ToolItem tltmNewItem_3 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_3.setImage(SWTResourceManager.getImage("/usr/share/icons/gnome/16x16/actions/media-playback-pause.png"));
		tltmNewItem_3.setText("Interrupt");
		
		ToolItem tltmNext = new ToolItem(toolBar, SWT.NONE);
		tltmNext.setText("Next");
		tltmNext.setImage(SWTResourceManager.getImage("/usr/share/icons/gnome/16x16/actions/go-next.png"));
		
		ToolItem tltmNewItem_2 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_2.setImage(SWTResourceManager.getImage("/usr/share/icons/gnome/16x16/actions/document-save.png"));
		tltmNewItem_2.setText("Complete");
		
		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.SEPARATOR);
		tltmNewItem_1.setText("New Item");
		
		ToolItem toolItem = new ToolItem(toolBar, SWT.RADIO);
		toolItem.setSelection(true);
		toolItem.setText("Tasks");
		toolItem.setImage(SWTResourceManager.getImage("/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/task.png"));
		
		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.RADIO);
		tltmNewItem.setText("Plan");
		tltmNewItem.setImage(SWTResourceManager.getImage("/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/calendar.png"));
		
		ToolItem tltmCalendar = new ToolItem(toolBar, SWT.RADIO);
		tltmCalendar.setText("Calendar");
		tltmCalendar.setImage(SWTResourceManager.getImage("/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/agenda.png"));
		
		ToolItem tltmStatistics = new ToolItem(toolBar, SWT.RADIO);
		tltmStatistics.setText("Statistics");
		tltmStatistics.setImage(SWTResourceManager.getImage("/usr/share/icons/gnome/16x16/apps/utilities-system-monitor.png"));
		
		Composite composite_3 = new Composite(composite_2, SWT.BORDER);
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_3.setLayoutData(BorderLayout.SOUTH);
		composite_3.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel_1 = new Label(composite_3, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setImage(SWTResourceManager.getImage("/usr/share/icons/gnome/24x24/actions/appointment.png"));
		lblNewLabel_1.setBounds(0, 0, 53, 12);
		
		Label lblNewLabel = new Label(composite_3, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Sans", 20, SWT.NORMAL));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(0, 0, 53, 12);
		lblNewLabel.setText("Final Report");
		new Label(composite_3, SWT.NONE);
		
		Label lblProjectOrbithub = new Label(composite_3, SWT.NONE);
		lblProjectOrbithub.setText("Project > OrbitHub");
		lblProjectOrbithub.setFont(SWTResourceManager.getFont("Sans", 11, SWT.NORMAL));
		lblProjectOrbithub.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblProjectOrbithub.setBounds(0, 0, 105, 22);

	}
}
