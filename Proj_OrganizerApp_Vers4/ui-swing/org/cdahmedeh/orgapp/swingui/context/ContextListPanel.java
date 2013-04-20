package org.cdahmedeh.orgapp.swingui.context;

import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.swingui.notification.LoadContextListRequest;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.jdesktop.swingx.CTreeTable;
import org.jdesktop.swingx.plaf.basic.core.BasicTransferable;

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
		@Subscribe public void loadContextList(LoadContextListRequest request) {
			prepareContextListTreeTableModel();
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
		
		enableDragRearrange();
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
		contextListTreeTable.setTreeTableModel(new ContextListTreeTableModel(dataContainer.getContextCategories()));
		contextListTreeTable.expandAll();
		contextListTreeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void enableDragRearrange() {
		contextListTreeTable.setDragEnabled(true);
		contextListTreeTable.setDropMode(DropMode.INSERT_ROWS);
		contextListTreeTable.setTransferHandler(new TransferHandler(){
			@Override
			public boolean canImport(TransferSupport support) {
				return true;
			}
			@Override
			public boolean importData(TransferSupport support) {
				return true;
			}
			@Override
			public int getSourceActions(JComponent c) {
				return MOVE;
			}
			@Override
			protected Transferable createTransferable(JComponent c) {
				return new StringSelection("");
			}
		});
	}
}
