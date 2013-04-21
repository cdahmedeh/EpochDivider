package org.cdahmedeh.orgapp.swingui.context;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.swingui.notification.LoadContextListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;

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
		createToolbar();
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

	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.SOUTH);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		toolbar.add(horizontalGlue);
				
		JButton addContextButton = new JButton("Add");
		addContextButton.setIcon(new ImageIcon(ContextListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/add.png")));
		toolbar.add(addContextButton);
		
		addContextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewContextToContextListTable();
			}
		});
	}
	
	/**
	 * Set the Table Model for the Context List Table.
	 */
	private void prepareContextListTableModel() {
		contextListTable.setModel(new ContextListTableModel(dataContainer.getContexts()));
		contextListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void refreshContextListTreeTable() {
		//TODO: This is not how we should refresh the table.
		contextListTable.repaint();
	}
	
	private void enableDragRearrange() {
		contextListTable.setDragEnabled(true);
		contextListTable.setDropMode(DropMode.INSERT_ROWS);
		contextListTable.setTransferHandler(new ContextListPanelTransferHandler(dataContainer.getContexts()));
	}

	//non-sequential methods 
	private void addNewContextToContextListTable() {
		//make sure that we are not already editing something
		if (contextListTable.isEditing()){
			contextListTable.getEditorComponent().requestFocus();
			return;
		}
		
		dataContainer.getContexts().add(new Context(""));
		refreshContextListTreeTable();
		
		contextListTable.editCellAt(contextListTable.getRowCount()-1, ContextListPanelDefaults.COLUMN_CONTEXT_NAME);
		
		Component editorComponent = contextListTable.getEditorComponent();
		if (editorComponent != null) {
			editorComponent.requestFocus();
		}
	}
}
