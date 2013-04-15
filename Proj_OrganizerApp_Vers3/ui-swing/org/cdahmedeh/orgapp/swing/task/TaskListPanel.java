package org.cdahmedeh.orgapp.swing.task;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.category.ProgressInfo;
import org.cdahmedeh.orgapp.swing.components.ProgressCellRenderer;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class TaskListPanel extends JPanel {
	private BigContainer bigContainer;
	
	private JTable table;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public TaskListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setShowHorizontalLines(false);

		table.setModel(new TaskListTableModel(bigContainer));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		

		
		scrollPane.setViewportView(table);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.SOUTH);
		
		textField = new JTextField();
		toolBar.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setIcon(new ImageIcon(TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/ui/icons/add.png")));
		toolBar.add(btnNewButton);
		
		table.getColumnModel().getColumn(3).setCellRenderer(new ProgressCellRenderer());


	}
}