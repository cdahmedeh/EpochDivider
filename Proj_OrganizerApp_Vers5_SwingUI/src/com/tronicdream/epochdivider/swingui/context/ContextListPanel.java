package com.tronicdream.epochdivider.swingui.context;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import com.tronicdream.epochdivider.types.container.DataContainer;
import com.tronicdream.epochdivider.types.context.Context;
import com.tronicdream.epochdivider.types.task.Task;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tronicdream.epochdivider.swingui.components.ColorHueCellRenderer;
import com.tronicdream.epochdivider.swingui.components.DurationCellEditor;
import com.tronicdream.epochdivider.swingui.components.RoundedTripleDurationCellRenderer;
import com.tronicdream.epochdivider.swingui.helpers.TableHelper;
import com.tronicdream.epochdivider.swingui.helpers.ToolbarHelper;
import com.tronicdream.epochdivider.swingui.main.CPanel;
import com.tronicdream.epochdivider.swingui.notification.ContextsChangedNotification;
import com.tronicdream.epochdivider.swingui.notification.SelectedContextChangedNotification;
import com.tronicdream.epochdivider.swingui.notification.TaskListPanelPostInitCompleteNotification;
import com.tronicdream.epochdivider.swingui.notification.TasksChangedNotification;

public class ContextListPanel extends CPanel {
	private static final long serialVersionUID = -8250528552031443184L;
	public ContextListPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus, "Context List");}

	@Override 
	protected Object getEventRecorder() {
		return new Object(){
			@Subscribe public void contextListChanged(ContextsChangedNotification request) {
				refreshContextListTreeTable();
			}
			@Subscribe public void taskListChanged(TasksChangedNotification notification){
				refreshContextListTreeTable();
			}
			@Subscribe public void selecteFirstContextWhenTaskListFinishesLoading(TaskListPanelPostInitCompleteNotification notification){
				selectItemInContextListTable(0);
			}
		};
	}

	// - Components -
	private JScrollPane contextListPane;
	private JTable contextListTable;
	
	// - Lists and Models - 
	private EventList<Context> contextEventList;
	private DefaultEventSelectionModel<Context> contextListSelectionModel;
	private ContextListTableFormat contextListTableFormat;
	
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_WIDTH, ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_HEIGHT));
		setLayout(new BorderLayout());
//		setBorder(UIManager.getBorder("ScrollPane.border"));
		setBorder(BorderFactory.createEmptyBorder());
		
		createContextListTable();
		createToolbar();
	}

	@Override
	protected void postWindowInit() {
		prepareContextListTableModelAndRenders();
		addNotificationWhenChangingContexts();		
		adjustContextListTableColumnWidths();
		enableDragRearrange();
	}

	private void createContextListTable() {
		contextListPane = new JScrollPane();
		contextListPane.setBorder(BorderFactory.createEmptyBorder());
		add(contextListPane, BorderLayout.CENTER);
		
		contextListTable = new JTable();
		contextListTable.setFillsViewportHeight(true);
		contextListTable.setIntercellSpacing(new Dimension(0,0));
		contextListTable.setShowHorizontalLines(true);
		contextListTable.setShowVerticalLines(false);
		contextListTable.setBackground(SystemColor.control);
		contextListTable.getTableHeader().setPreferredSize(new Dimension(0,0));
		contextListTable.setRowHeight(20);
		contextListPane.setViewportView(contextListTable);
	}

	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBackground(contextListTable.getBackground());
		add(toolbar, BorderLayout.SOUTH);
		
		ToolbarHelper.createToolbarHorizontalGlue(toolbar);
		JButton addContextButton = ToolbarHelper.createToolbarButton(toolbar, "Add Context", ContextListPanel.class.getResource("/com/tronicdream/epochdivider/imt/icons/add.gif"));
		addContextButton.setBackground(contextListTable.getBackground());
		addContextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewContextToContextListTable();
			}
		});
	}
	
	private void prepareContextListTableModelAndRenders() {
		//Create EventList and Table Format. Put all Contexts from data container.
		contextEventList = new BasicEventList<>();
		contextListTableFormat = new ContextListTableFormat(dataContainer);
		contextEventList.addAll(dataContainer.getContexts());
		
		//Prepare and set Table Model.
		AdvancedTableModel<Context> advancedTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(contextEventList, contextListTableFormat);
		contextListTable.setModel(advancedTableModel);
		
		//Setup editors and renderers
		TableColumn dueDateColumn = contextListTable.getColumnModel().getColumn(ContextListPanelDefaults.COLUMN_CONTEXT_COLOR);
		dueDateColumn.setCellRenderer(new ColorHueCellRenderer());
		
		TableColumn progressColumn = contextListTable.getColumnModel().getColumn(ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS);
		progressColumn.setCellRenderer(new RoundedTripleDurationCellRenderer());
		progressColumn.setCellEditor(new DurationCellEditor(new JTextField()));
	}

	private void addNotificationWhenChangingContexts() {
		contextListSelectionModel = new DefaultEventSelectionModel<>(contextEventList);
		contextListTable.setSelectionModel(contextListSelectionModel);
		contextListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		contextListSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				notifyThatSelectedContextChanged();
			}
		});
	}
	
	private void adjustContextListTableColumnWidths() {
		contextListTable.getColumnModel().getColumn(ContextListPanelDefaults.COLUMN_CONTEXT_COLOR).setMaxWidth(ContextListPanelDefaults.COLUMN_CONTEXT_COLOR_MAX_WIDTH);
		contextListTable.getColumnModel().getColumn(ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS).setMaxWidth(ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS_MAX_WIDTH);
	}
	
	private void enableDragRearrange() {
		contextListTable.setDragEnabled(true);
		contextListTable.setTransferHandler(new ContextListPanelTransferHandler());
	}


	// -- non-sequential methods --
	
	private void refreshContextListTreeTable() {
		//Make sure the original context will be reselected
		int row = contextListTable.getSelectedRow();
		
		//TODO: Correctly refresh table.
		contextEventList.clear();
		contextEventList.addAll(dataContainer.getContexts());
		
		//Reset the selection to what was original selected before.
		selectItemInContextListTable(row);
	}
	

	private void notifyThatSelectedContextChanged() {
		Context selectedContext = getSelectedContextInTable();
		if (selectedContext != null) {
			dataContainer.setSelectedContext(selectedContext);
			eventBus.post(new SelectedContextChangedNotification());
		}
	}
	
	private void addNewContextToContextListTable() {
		//If we are already editing a task, then just set focus to the editor.
		if (contextListTable.isEditing()){
			contextListTable.getEditorComponent().requestFocus();
			return;
		}
		
		//Create a new context, add it to the data container, and refresh the context list table.
		dataContainer.createNewBlankContext();
		refreshContextListTreeTable();
		
		//Init. editing the title of the new context and focus the editor.
		int rowOfNewContext = contextListTable.getRowCount()-1;
		contextListTable.editCellAt(rowOfNewContext, ContextListPanelDefaults.COLUMN_CONTEXT_NAME);

		Component editorComponent = contextListTable.getEditorComponent();
		if (editorComponent != null) {
			editorComponent.requestFocus();
		}

		//Scroll to the context that is being added.
		TableHelper.scrollTableToCellAt(contextListTable, rowOfNewContext, ContextListPanelDefaults.COLUMN_CONTEXT_NAME);
		
		//Select the item that is was edited.
		selectItemInContextListTable(rowOfNewContext);
	}
	
	private Context getSelectedContextInTable(){
		EventList<Context> selectedContexts = contextListSelectionModel.getSelected();
		if (selectedContexts.size() == 1) {
			return selectedContexts.get(0);
		} else {
			return null;
		}
	}
	
	private void selectItemInContextListTable(int n){
		if (n>=0 && n<contextListTable.getRowCount()){
			contextListTable.getSelectionModel().setSelectionInterval(n, n);
		}
	}
	
	private Context getContextAtRowInTable(int row){
		if (row >= 0 && row < contextEventList.size()){
			return contextEventList.get(row);			
		} else {
			return null;
		}
	}
	
	/**
	 * TransferHandler for allowing dragging and dropping between elements in the
	 * Context list tree.
	 *  
	 * @author Ahmed El-Hajjar
	 */
	class ContextListPanelTransferHandler extends TransferHandler {
		private static final long serialVersionUID = 247239613980407558L;
		
		@Override
		public boolean canImport(TransferSupport support) {
			//Currently, only dataContainer can be dropped for reordering.
			if (support.getTransferable().isDataFlavorSupported(new DataFlavor(Context.class, "Context"))) {
				contextListTable.setDropMode(DropMode.INSERT_ROWS);
				return true;
			} else if (support.getTransferable().isDataFlavorSupported(new DataFlavor(Task.class, "Task"))) {
				contextListTable.setDropMode(DropMode.ON);
				
				//Make sure that we can drop only on selectable contexts.
				int row = ((JTable.DropLocation)support.getDropLocation()).getRow();
				Context context = getContextAtRowInTable(row);
				return dataContainer.emContextIsAssignable(context);
			}
			return false;
		}

		@Override
		public boolean importData(TransferSupport support) {
			if (support.getTransferable().isDataFlavorSupported(new DataFlavor(Context.class, "Context"))) {
				//Get the context that was transfered.
				Context context = null;
				try {
					context = (Context) support.getTransferable().getTransferData(new DataFlavor(Context.class, "Context"));
				} catch (UnsupportedFlavorException | IOException e) {
					return false;
				}

				//Put the context into the new location.
				int row = ((JTable.DropLocation)support.getDropLocation()).getRow();
				int newIndexForMovedContext = dataContainer.moveContextToRowAndGiveNewIndex(context, row);

				//Update table and select the moved context.
				eventBus.post(new ContextsChangedNotification());
				selectItemInContextListTable(newIndexForMovedContext);

				return true;
			} else if (support.getTransferable().isDataFlavorSupported(new DataFlavor(Task.class, "Task"))) {
				//Get the task that was dragged.
				Task task = null;
				try {
					task = (Task) support.getTransferable().getTransferData(new DataFlavor(Task.class, "Task"));
				} catch (UnsupportedFlavorException | IOException e) {
					return false;
				}
				
				//Get the context that was dropped on
				int row = ((JTable.DropLocation)support.getDropLocation()).getRow();
				Context context = getContextAtRowInTable(row);
				
				if (context == null) {
					return false;
				}
				
				//Change the context of task that was dragged in.
				boolean changed = dataContainer.emTaskSetContext(task, context);
				
				//Let everyone know that we refreshed.
				eventBus.post(new ContextsChangedNotification());
				eventBus.post(new TasksChangedNotification());
				
				return changed;
			}
			
			return false;
		}

		@Override
		public int getSourceActions(JComponent c) {
			return COPY_OR_MOVE;
		}

		@Override
		protected Transferable createTransferable(JComponent c) {
			Context context = getSelectedContextInTable();
			if (context == null) { 
				return null;
			} else {
				return new ContextTransferable(context);
			}
		}
	}
}
