package org.cdahmedeh.orgapp.swingui.context;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import org.cdahmedeh.orgapp.swingui.components.ColorHueCellRenderer;
import org.cdahmedeh.orgapp.swingui.components.DurationCellEditor;
import org.cdahmedeh.orgapp.swingui.components.TripleDurationCellRenderer;
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
			@Subscribe public void selecteFirstContextWhenTaskListFinishesLoading(TaskListPanelPostInitCompleteNotification notification){
				selectItemInContextListTable(0);
			}
			@Subscribe public void tasksUpdated(TasksChangedNotification notification){
				refreshContextListTreeTable();
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
		//Prepare Event List
		contextEventList = new BasicEventList<>();
		contextEventList.addAll(dataContainer.getContexts());
		
		contextListTableFormat = new ContextListTableFormat(dataContainer);
		AdvancedTableModel<Context> advancedTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(contextEventList, contextListTableFormat);
		contextListTable.setModel(advancedTableModel);
		
		//Setup editor and renders for some columns
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
				selectedContextChangedNotify();
			}
		});
	}
	
	private void adjustContextListTableColumnWidths() {
		contextListTable.getColumnModel().getColumn(ContextListPanelDefaults.COLUMN_CONTEXT_COLOR).setMaxWidth(1);
	}
	
	private void enableDragRearrange() {
		contextListTable.setDragEnabled(true);
		contextListTable.setDropMode(DropMode.INSERT_ROWS);
		contextListTable.setTransferHandler(new ContextListPanelTransferHandler(dataContainer.getContexts(), eventBus));
	}


	// -- non-sequential methods --
	
	private void refreshContextListTreeTable() {
		//TODO: Correctly refresh table.
		contextEventList.clear();
		contextEventList.addAll(dataContainer.getContexts());
		contextListTableFormat.updateReferences(dataContainer);
		contextListTable.setTransferHandler(new ContextListPanelTransferHandler(dataContainer.getContexts(), eventBus));
	}
	

	private void selectedContextChangedNotify() {
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
		dataContainer.getContexts().add(new Context(""));
		refreshContextListTreeTable();
		
		//Init. editing the title of the new context and focus the editor.
		contextListTable.editCellAt(contextListTable.getRowCount()-1, ContextListPanelDefaults.COLUMN_CONTEXT_NAME);
		
		Component editorComponent = contextListTable.getEditorComponent();
		if (editorComponent != null) {
			editorComponent.requestFocus();
		}
		
		//TODO: Where does selection go after editing is done?
	}
	
	private Context getSelectedContextInTable(){
		EventList<Context> selectedContexts = contextListSelectionModel.getSelected();
		if (selectedContexts.size() == 0) {
			return null;
		} else if (selectedContexts.size() == 1) {
			return selectedContexts.get(0);
		} else {
			return null;
		}
	}
	
	private void selectItemInContextListTable(int n){
		contextListTable.getSelectionModel().setSelectionInterval(n, n);
	}
}
