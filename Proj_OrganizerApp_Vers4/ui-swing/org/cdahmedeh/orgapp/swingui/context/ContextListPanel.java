package org.cdahmedeh.orgapp.swingui.context;

import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.swingui.notification.LoadContextListRequest;
import org.jdesktop.swingx.JXTreeTable;
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
	private JXTreeTable contextListTreeTable;

	/**
	 * Create the panel.
	 */
	public ContextListPanel() {
		setPreferredSize(new Dimension(ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_WIDTH, -1));
		setLayout(new BorderLayout());
		
		createContextListTreeTable();
		
		enableDragRearrange();
	}

	private void createContextListTreeTable() {
		contextListPane = new JScrollPane();
		add(contextListPane, BorderLayout.CENTER);
		
		contextListTreeTable = new JXTreeTable();
		contextListTreeTable.setFillsViewportHeight(true);
		contextListPane.setViewportView(contextListTreeTable);
	}

	/**
	 * Set the TreeTable Model for the Context List Table.
	 */
	private void prepareContextListTreeTableModel() {
		contextListTreeTable.setTreeTableModel(new ContextListTreeTableModel(TestDataGenerator.generateListOfContextCategories()));
		contextListTreeTable.expandAll();
	}
	
	private void enableDragRearrange() {
//		contextListTreeTable.setDragEnabled(true);
//		contextListTreeTable.setDropMode(DropMode.INSERT_ROWS);
//		contextListTreeTable.setTransferHandler(new TransferHandler(){
//			@Override
//			public boolean canImport(TransferSupport support) {
//				return true;
//			}
//			@Override
//			public boolean importData(TransferSupport support) {
//				return true;
//			}
//			@Override
//			public int getSourceActions(JComponent c) {
//				return MOVE;
//			}
//			@Override
//			protected Transferable createTransferable(JComponent c) {
//				return new StringSelection("");
//			}
//		});
	}
}
