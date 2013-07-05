package org.cdahmedeh.orgapp.swingui.contextlist;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.cdahmedeh.orgapp.imt.icons.Icons;
import org.cdahmedeh.orgapp.types.containers.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;

import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = -3908797309691176770L;
	
	// Data Container
	private DataContainer dataContainer;

	// Components
	private JTable contextListTable;

	public ContextListPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;

		preparePanel();
		createToolbar();
		createContextListTable();
		prepareContextListTableModel();	
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

	private void createContextListTable() {
		contextListTable = new JTable();

		JScrollPane contextListScrollPane = new JScrollPane();
		add(contextListScrollPane, BorderLayout.CENTER);
		contextListScrollPane.setViewportView(contextListTable);
	}
	
	private void prepareContextListTableModel() {
		EventList<Context> contextEventList = new BasicEventList<>();
		ContextListTableFormat contextListTableFormat = new ContextListTableFormat();
		contextEventList.addAll(dataContainer.getContexts());
		
		AdvancedTableModel<Context> advancedTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(contextEventList, contextListTableFormat);
		contextListTable.setModel(advancedTableModel);
	}
}
