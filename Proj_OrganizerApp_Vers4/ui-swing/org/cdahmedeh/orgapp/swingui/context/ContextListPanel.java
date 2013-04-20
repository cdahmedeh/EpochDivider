package org.cdahmedeh.orgapp.swingui.context;

import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;

import org.cdahmedeh.orgapp.swingui.notification.LoadContextListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.jdesktop.swingx.CTreeTable;

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
	private CTreeTable contextListTreeTable;

	// - Data -
	private DataContainer dataContainer;

	/**
	 * Create the panel.
	 */
	public ContextListPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		setPreferredSize(new Dimension(ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_WIDTH, ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		
		createContextListTreeTable();		
	}
	
	private void postInit() {
		prepareContextListTreeTableModel();
//		enableDragRearrange();
	}

	private void createContextListTreeTable() {
		contextListPane = new JScrollPane();
		add(contextListPane, BorderLayout.CENTER);
		
		contextListTreeTable = new CTreeTable();
		contextListTreeTable.setShowGrid(true, true);
		contextListTreeTable.setFillsViewportHeight(true);
		contextListPane.setViewportView(contextListTreeTable);
	}

	/**
	 * Set the TreeTable Model for the Context List Table.
	 */
	private void prepareContextListTreeTableModel() {
		refreshContextListTreeTable();
		contextListTreeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void refreshContextListTreeTable() {
		contextListTreeTable.setTreeTableModel(new ContextListTreeTableModel(dataContainer.getRootContext()));
		contextListTreeTable.expandAll();
	}
	
//	private void enableDragRearrange() {
//		contextListTreeTable.setDragEnabled(true);
//		contextListTreeTable.setDropMode(DropMode.INSERT_ROWS);
//		contextListTreeTable.setTransferHandler(new ContextListPanelTransferHandler(contextListTreeTable, eventBus));
//	}
}
