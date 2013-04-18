package org.cdahmedeh.orgapp.swingui.context;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.types.context.Context;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventListModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = -8250528552031443184L;
	
	// - Components -
	private JScrollPane contextListPane;
	private JTable contextListTable;

	/**
	 * Create the panel.
	 */
	public ContextListPanel() {
		setPreferredSize(new Dimension(ContextListPanelDefaults.DEFAULT_CONTEXT_PANEL_WIDTH, -1));
		setLayout(new BorderLayout());
		
		createContextListTable();
		prepareContextListTableModel();
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
		TableFormat<Context> contextTableFormat = new ContextListTableFormat();
		EventList<Context> contextEventList = new BasicEventList<>();
		contextEventList.addAll(TestDataGenerator.generateListOfContexts());
		
		AdvancedTableModel<Context> contextListTableModel = 
				GlazedListsSwing.eventTableModelWithThreadProxyList(contextEventList, contextTableFormat);
		
		contextListTable.setModel(contextListTableModel);
	}
}
