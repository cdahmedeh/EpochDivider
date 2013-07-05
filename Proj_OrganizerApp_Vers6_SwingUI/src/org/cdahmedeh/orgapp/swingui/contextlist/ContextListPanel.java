package org.cdahmedeh.orgapp.swingui.contextlist;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.cdahmedeh.orgapp.imt.icons.Icons;
import org.cdahmedeh.orgapp.types.containers.DataContainer;

import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = -3908797309691176770L;
	
	// Data Container
	private DataContainer dataContainer;

	// Data Models
	private ContextListTableModel contextListTableModel;
	
	public ContextListPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;

		preparePanel();
		createToolbar();
		prepareContextListTableModel();	
		createContextListTable();
	}

	private void preparePanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(ContextListConstants.CONTEXT_LIST_DEFAULT_WIDTH, ContextListConstants.CONTEXT_LIST_DEFAULT_HEIGHT));
	}

	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.SOUTH);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		toolbar.add(horizontalGlue);
		
		JButton btnAddContext = new JButton("Add Context");
		btnAddContext.setIcon(new ImageIcon(ContextListPanel.class.getResource(Icons.ADD)));
		toolbar.add(btnAddContext);
	}
	
	private void prepareContextListTableModel() {
		contextListTableModel = new ContextListTableModel(dataContainer.getContexts());
	}
	
	private void createContextListTable() {
		JTable contextListTreeTable = new JTable();
		contextListTreeTable.setModel(contextListTableModel);

		JScrollPane contextListScrollPane = new JScrollPane();
		add(contextListScrollPane, BorderLayout.CENTER);
		contextListScrollPane.setViewportView(contextListTreeTable);
	}
}
