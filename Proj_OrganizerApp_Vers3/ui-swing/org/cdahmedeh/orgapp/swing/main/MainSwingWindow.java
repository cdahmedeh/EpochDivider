package org.cdahmedeh.orgapp.swing.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.containers.ContextContainer;
import org.cdahmedeh.orgapp.containers.TaskContainer;
import org.cdahmedeh.orgapp.swing.category.ContextListPanel;
import org.cdahmedeh.orgapp.swing.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import java.awt.BorderLayout;
import java.awt.Font;

public class MainSwingWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			setUIFont (new javax.swing.plaf.FontUIResource("Segoe UI",Font.PLAIN,12));
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainSwingWindow window = new MainSwingWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainSwingWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JXMultiSplitPane jxMultiSplitPane = new JXMultiSplitPane();		
		jxMultiSplitPane.setModel(new DefaultSplitPaneModel());
		frame.getContentPane().add(jxMultiSplitPane);
		
		View currentView = new View(new LocalDate(2013,3,25), new LocalDate(2013,3,25).plusDays(6), new LocalTime(12, 0, 0), new LocalTime(23, 59, 59, 999));
		
		TaskContainer taskContainer = new TaskContainer();
		Task task01 = new Task("Do Work");
		Task task02 = new Task("Do More Work");
		taskContainer.addTask(task01);
		taskContainer.addTask(task02);
		
		ContextContainer contextContainer = new ContextContainer();
		contextContainer.addContext(new Context("Essentials"));
		BigContainer bigContainer = new BigContainer(taskContainer, contextContainer, currentView);
		
		ContextListPanel contextListPanel = new ContextListPanel(bigContainer);
		jxMultiSplitPane.add(contextListPanel, DefaultSplitPaneModel.LEFT);
		
		TaskListPanel taskListPanel = new TaskListPanel(bigContainer);
		jxMultiSplitPane.add(taskListPanel, DefaultSplitPaneModel.BOTTOM);
	}
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	} 
}