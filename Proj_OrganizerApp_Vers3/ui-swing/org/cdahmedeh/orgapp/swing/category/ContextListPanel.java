package org.cdahmedeh.orgapp.swing.category;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.components.ProgressCellRenderer;
import org.cdahmedeh.orgapp.swing.main.SwingUIDefaults;
import org.cdahmedeh.orgapp.swing.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.category.Context;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = 4463133789121204761L;

	//Data
	private BigContainer bigContainer;
	
	//Components
	private JScrollPane mainPane;
	private JTable contextListTable;
	
	public ContextListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		
		setPreferredSize(new Dimension(SwingUIDefaults.CONTEXT_PANEL_WIDTH, 0));
		setLayout(new BorderLayout(0, 0));
		
		mainPane = new JScrollPane();
		add(mainPane);
		
		createContextListTable();
		mainPane.setViewportView(contextListTable);
		
		createBottomBar();
	}

	private void createContextListTable() {
		contextListTable = new JTable();
		contextListTable.setFillsViewportHeight(true);
		contextListTable.setShowHorizontalLines(false);
		contextListTable.setShowVerticalLines(false);
		contextListTable.setModel(new ContextListTableModel(bigContainer));
		try {
			contextListTable.getColumnModel().getColumn(1).setCellRenderer(new ProgressCellRenderer());
		} catch (NullPointerException e){
			
		}
	}
	
	private void createBottomBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.SOUTH);
		
		final JTextField textField = new JTextField();
		toolBar.add(textField);
		textField.setColumns(10);
		
		JButton btnAddContextButton = new JButton("Add");
		btnAddContextButton.setIcon(new ImageIcon(TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/ui/icons/add.png")));
		toolBar.add(btnAddContextButton);
		
		btnAddContextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bigContainer.getContextContainer().addContext(new Context(textField.getText()));
				textField.setText("");
				contextListTable.repaint(); //TODO: Is there a better way for refresh?
			}
		});
	}
}
