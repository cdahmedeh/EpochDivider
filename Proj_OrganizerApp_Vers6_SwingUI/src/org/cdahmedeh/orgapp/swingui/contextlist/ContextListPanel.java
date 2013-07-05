package org.cdahmedeh.orgapp.swingui.contextlist;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.types.containers.DataContainer;

import de.sciss.treetable.j.TreeTable;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = -3908797309691176770L;
	
	// Data Container
	private DataContainer dataContainer;

	// Data Models
	private ContextListTreeModel contextListTreeModel;
	private ContextListTreeColumnModel contextListTreeColumnModel;
	
	public ContextListPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		preparePanel();
		prepareContextListTreeTableModels();	
		createContextListTreeTable();
	}

	private void preparePanel() {
		setLayout(new BorderLayout());
	}

	private void prepareContextListTreeTableModels() {
		contextListTreeModel = new ContextListTreeModel(dataContainer.getCategories());
		contextListTreeColumnModel = new ContextListTreeColumnModel();
	}
	
	private void createContextListTreeTable() {
		TreeTable contextListTreeTable = new TreeTable(contextListTreeModel, contextListTreeColumnModel);
		contextListTreeTable.setRootVisible(false);
		contextListTreeTable.setShowsRootHandles(true);
		contextListTreeTable.setNodeSortingEnabled(false);

		JScrollPane contextListScrollPane = new JScrollPane();
		add(contextListScrollPane);
		contextListScrollPane.setViewportView(contextListTreeTable);
	}
}
