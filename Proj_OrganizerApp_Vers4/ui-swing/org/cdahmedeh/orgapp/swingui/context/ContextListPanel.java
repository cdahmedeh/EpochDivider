package org.cdahmedeh.orgapp.swingui.context;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.swingui.components.ColorHueCellRenderer;
import org.cdahmedeh.orgapp.swingui.helpers.ToolbarHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
import org.cdahmedeh.orgapp.swingui.notification.SelectedContextChangedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

public class ContextListPanel extends CPanel {
	private static final long serialVersionUID = -8250528552031443184L;
	public ContextListPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}

	@Override
	protected Object getEventRecorder() {
		return new Object(){
			@Subscribe public void refreshContextList(RefreshContextListRequest request) {
				refreshContextListTreeTable();
			}
		};
	}

	// - Components -
	private JScrollPane contextListPane;
	private JTable contextListTable;
	
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_WIDTH, ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		
		createContextListTable();
		createToolbar();
	}

	@Override
	protected void postWindowInit() {
		addNotificationWhenChangingContexts();		
		prepareContextListTableModel();
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
		JButton addContextButton = ToolbarHelper.createToolbarButton(toolbar, "Add", ContextListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/add.png"));
		
		addContextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewContextToContextListTable();
			}
		});
	}
	
	private void prepareContextListTableModel() {
		contextListTable.setModel(new ContextListTableModel(dataContainer.getContexts()));
		contextListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumn dueDateColumn = contextListTable.getColumnModel().getColumn(ContextListPanelDefaults.COLUMN_CONTEXT_COLOR);
		dueDateColumn.setCellRenderer(new ColorHueCellRenderer());
	}

	private void adjustContextListTableColumnWidths() {
		contextListTable.getColumnModel().getColumn(ContextListPanelDefaults.COLUMN_CONTEXT_COLOR).setMaxWidth(1);
	}
	
	private void enableDragRearrange() {
		contextListTable.setDragEnabled(true);
		contextListTable.setDropMode(DropMode.INSERT_ROWS);
		contextListTable.setTransferHandler(new ContextListPanelTransferHandler(dataContainer.getContexts()));
	}

	private void addNotificationWhenChangingContexts() {
		contextListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedIndex = contextListTable.getSelectedRow();
				Context selectedContext = dataContainer.getContexts().get(selectedIndex);
				dataContainer.setSelectedContext(selectedContext);
				eventBus.post(new SelectedContextChangedNotification());
			}
		});
	}
	
	
	// -- non-sequential methods --
	
	private void refreshContextListTreeTable() {
		//TODO: Correctly refresh table.
		contextListTable.repaint();
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
		
		//Init. editing the title of the new tasks and focus the editor.
		contextListTable.editCellAt(contextListTable.getRowCount()-1, ContextListPanelDefaults.COLUMN_CONTEXT_NAME);
		
		Component editorComponent = contextListTable.getEditorComponent();
		if (editorComponent != null) {
			editorComponent.requestFocus();
		}
	}
}
