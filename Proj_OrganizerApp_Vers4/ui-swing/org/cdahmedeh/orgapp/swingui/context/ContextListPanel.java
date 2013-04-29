package org.cdahmedeh.orgapp.swingui.context;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

import org.cdahmedeh.orgapp.swingui.components.ColorHueCellRenderer;
import org.cdahmedeh.orgapp.swingui.components.DurationCellEditor;
import org.cdahmedeh.orgapp.swingui.components.TripleDurationCellRenderer;
import org.cdahmedeh.orgapp.swingui.helpers.TableHelper;
import org.cdahmedeh.orgapp.swingui.helpers.ToolbarHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
import org.cdahmedeh.orgapp.swingui.notification.SelectedContextChangedNotification;
import org.cdahmedeh.orgapp.swingui.notification.TaskListPanelPostInitCompleteNotification;
import org.cdahmedeh.orgapp.swingui.notification.TasksChangedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class ContextListPanel extends CPanel {
	private static final long serialVersionUID = -8250528552031443184L;
	public ContextListPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}

	@Override 
	protected Object getEventRecorder() {
		return new Object(){
			@Subscribe public void refreshContextList(RefreshContextListRequest request) {
				refreshContextListTreeTable();
			}
			@Subscribe public void tasksUpdated(TasksChangedNotification notification){
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
		add(contextListPane, BorderLayout.CENTER);
		
		contextListTable = new JTable();
		contextListTable.setFillsViewportHeight(true);
		contextListPane.setViewportView(contextListTable);
	}

	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.SOUTH);
		
		ToolbarHelper.createToolbarHorizontalGlue(toolbar);
		JButton addContextButton = ToolbarHelper.createToolbarButton(toolbar, "Add Context", ContextListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/add.gif"));
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
		progressColumn.setCellRenderer(new TripleDurationCellRenderer());
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
	}
	
	private void enableDragRearrange() {
		contextListTable.setDragEnabled(true);
		contextListTable.setDropMode(DropMode.INSERT_ROWS);
		contextListTable.setTransferHandler(new ContextListPanelTransferHandler());
	}


	// -- non-sequential methods --
	
	private void refreshContextListTreeTable() {
		//TODO: Correctly refresh table.
		contextEventList.clear();
		contextListTableFormat.updateReferences(dataContainer);
		contextEventList.addAll(dataContainer.getContexts());
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
		TableHelper.scrollToVisible(contextListTable, rowOfNewContext, ContextListPanelDefaults.COLUMN_CONTEXT_NAME);
		
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
			return support.getTransferable().isDataFlavorSupported(new DataFlavor(Context.class, "Context"));
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

				//Put the context in that location.
				int row = ((JTable.DropLocation)support.getDropLocation()).getRow();
				int newIndexForMovedContext = dataContainer.moveContextToRowAndGiveNewIndex(context, row);

				//Update table and select the moved context.
				eventBus.post(new RefreshContextListRequest());
				selectItemInContextListTable(newIndexForMovedContext);

				return true;
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
