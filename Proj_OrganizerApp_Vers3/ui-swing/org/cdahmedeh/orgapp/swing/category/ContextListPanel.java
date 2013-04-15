package org.cdahmedeh.orgapp.swing.category;

import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.main.SwingUIDefaults;
import org.cdahmedeh.orgapp.swing.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.category.Context;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = 4463133789121204761L;

	private BigContainer bigContainer;
	
	private JScrollPane mainPane;
	private JTable contextListTable;
	
	public ContextListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		
		this.setPreferredSize(new Dimension(SwingUIDefaults.CONTEXT_PANEL_WIDTH, 0));
		this.setLayout(new BorderLayout(0, 0));
		
		mainPane = new JScrollPane();
		add(mainPane);
		
		createContextListTable();
		mainPane.setViewportView(contextListTable);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.SOUTH);
		
		final JTextField textField = new JTextField();
		toolBar.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setIcon(new ImageIcon(TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/ui/icons/add.png")));
		toolBar.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bigContainer.getContextContainer().addContext(new Context(textField.getText()));
				textField.setText("");
				contextListTable.repaint(); //TODO: Is there a better way for refresh?
			}
		});
	}

	private void createContextListTable() {
		contextListTable = new JTable(){};
		contextListTable.setFillsViewportHeight(true);
		contextListTable.setShowHorizontalLines(false);
		contextListTable.setShowVerticalLines(false);
		contextListTable.setModel(new ContextListTableModel(bigContainer));
		contextListTable.getColumnModel().getColumn(1).setCellRenderer(new ProgressCellRenderer());
	}
}
