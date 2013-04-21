package org.cdahmedeh.orgapp.swingui.context;

import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.swingui.notification.LoadContextListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = -8250528552031443184L;
	
	// - EventBus -
	private EventBus eventBus;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(new EventRecorder());
	}
	
	class EventRecorder{
		@Subscribe public void loadContextListPanel(LoadContextListPanelRequest request) {
			postInit();
		}
		@Subscribe public void refreshContextList(RefreshContextListRequest request) {
			refreshContextListTreeTable();
		}
	}
	
	// - Components -
	private JScrollPane contextListPane;
	private JTable contextListTable;

	// - Data -
	private DataContainer dataContainer;

	/**
	 * Create the panel.
	 */
	public ContextListPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		setPreferredSize(new Dimension(ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_WIDTH, ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		
		createContextListTable();		
	}
	
	private void postInit() {
		prepareContextListTableModel();
		enableDragRearrange();
	}

	private void createContextListTable() {
		contextListPane = new JScrollPane();
		add(contextListPane, BorderLayout.CENTER);
		
		contextListTable = new JTable();
		contextListTable.setFillsViewportHeight(true);
		contextListPane.setViewportView(contextListTable);
	}

	/**
	 * Set the Table Model for the Context List Table.
	 */
	private void prepareContextListTableModel() {
		refreshContextListTreeTable();
		contextListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void refreshContextListTreeTable() {
		contextListTable.setModel(new ContextListTableModel(dataContainer.getContexts()));
	}
	
	private void enableDragRearrange() {
		contextListTable.setDragEnabled(true);
		contextListTable.setDropMode(DropMode.INSERT_ROWS);
		contextListTable.setTransferHandler(new ContextListPanelTransferHandler(dataContainer.getContexts()));
	}
}
