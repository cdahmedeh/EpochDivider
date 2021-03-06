package org.cdahmedeh.orgapp.swing.category;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.components.ProgressCellRenderer;
import org.cdahmedeh.orgapp.swing.main.SwingUIDefaults;
import org.cdahmedeh.orgapp.swing.notifications.DataChangedNotification;
import org.cdahmedeh.orgapp.swing.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.ui.icons.Icons;

import com.google.common.eventbus.EventBus;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = 4463133789121204761L;

	//Data
	private BigContainer bigContainer;
	
	//Components
	private JScrollPane mainPane;
	private JTable contextListTable;
	
	private EventBus eventBus = null;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
//		this.eventBus.register(new EventRecorder());
	}
	
	public ContextListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		
		setPreferredSize(new Dimension(SwingUIDefaults.CONTEXT_PANEL_WIDTH, SwingUIDefaults.CONTEXT_PANEL_HEIGHT));
		setLayout(new BorderLayout(0, 0));
		
		mainPane = new JScrollPane();
		add(mainPane);
		
		createContextListTable();
		enableDropIntoContextListTable();
		mainPane.setViewportView(contextListTable);
		
//		createBottomBar();
	}

	private void createContextListTable() {
		contextListTable = new JTable();
		contextListTable.setForeground(SystemColor.inactiveCaptionText);
		contextListTable.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		
		contextListTable.setFillsViewportHeight(true);
		contextListTable.setShowHorizontalLines(true);
		contextListTable.setShowVerticalLines(false);
		contextListTable.setRowHeight(20);
		
		
		contextListTable.getTableHeader().setVisible(false);
		contextListTable.getTableHeader().setPreferredSize(new Dimension(-1, 0));
		
		contextListTable.setModel(new ContextListTableModel(bigContainer));
		contextListTable.getColumnModel().getColumn(ContextListColumns.PROGRESS).setCellRenderer(new ProgressCellRenderer());
		contextListTable.getColumnModel().setColumnMargin(10);
		
		contextListTable.setGridColor(new Color(204, 204, 204));

		//TODO: using mouse click to different from dragging in
		contextListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					bigContainer.setCurrentContext((bigContainer.getContextContainer().getContextFromName((String)contextListTable.getModel().getValueAt(contextListTable.getSelectedRow(), 0))));
					eventBus.post(new DataChangedNotification());

			}
		});
	}

	private void enableDropIntoContextListTable() {
		contextListTable.setTransferHandler(new TransferHandler(){
			@Override
			public boolean canImport(TransferSupport support) {
				return true;
			}
			
			@Override
			public boolean importData(TransferSupport support) {
				Transferable trans = support.getTransferable();
				System.out.println(trans);
					try {
						System.out.println("WE ARE HERE");
						Object transferData = trans.getTransferData(DataFlavor.stringFlavor);
						Task selectedTask = bigContainer.getTaskContainer().getTaskFromId(Integer.valueOf((String) transferData));
						selectedTask.setContext(bigContainer.getContextContainer().getContextFromName((String)contextListTable.getModel().getValueAt(contextListTable.getSelectedRow(), 0)));
					} catch (Exception e){
						
					}
				return true;
			}
		});
	}
	
	private void createBottomBar() {
		JToolBar bottomBar = new JToolBar();
		bottomBar.setFloatable(false);
		add(bottomBar, BorderLayout.SOUTH);
		
		final JTextField addContextTextField = new JTextField();
		bottomBar.add(addContextTextField);
		
		JButton addContextButton = new JButton("Add");
		addContextButton.setIcon(new ImageIcon(TaskListPanel.class.getResource(Icons.ADD)));
		bottomBar.add(addContextButton);
		
		addContextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bigContainer.getContextContainer().addContext(new Context(addContextTextField.getText()));
				addContextTextField.setText("");
				contextListTable.repaint(); //TODO: Is there a better way for refresh?
			}
		});
	}
}
