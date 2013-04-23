package org.cdahmedeh.orgapp.swingui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.swingui.context.ContextListPanel;
import org.cdahmedeh.orgapp.swingui.notification.LoadContextListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.LoadTaskListPanelRequest;
import org.cdahmedeh.orgapp.swingui.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.jidesoft.swing.JideSplitPane;

import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class MainWindow {
	// - Events and Logging -
	private EventBus eventBus;
	private Logger logger = Logger.getLogger(this.getClass());
	
	// - Components -
	private JFrame frame;

	// - Data -
	private DataContainer dataContainer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		BasicConfigurator.configure();
		logger.setLevel(Level.INFO);
		logger.info("Starting application...");
		
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		dataContainer = TestDataGenerator.generateDataContainer();
		eventBus = new EventBus();
		
		initialize();
		this.frame.setVisible(true);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				eventBus.post(new LoadContextListPanelRequest());
				eventBus.post(new LoadTaskListPanelRequest());
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Epoch Divider");

		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JideSplitPane mainSplitPane = new JideSplitPane();
		mainSplitPane.setContinuousLayout(true);
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
		
		ContextListPanel contextListPanel = new ContextListPanel(dataContainer);
		contextListPanel.setEventBus(eventBus);
		mainSplitPane.addPane(contextListPanel);
		
		JideSplitPane rightSplitPane = new JideSplitPane(JideSplitPane.VERTICAL_SPLIT);
		rightSplitPane.setContinuousLayout(true);
		mainSplitPane.addPane(rightSplitPane);
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(500, 500));
		emptyPanel.setBackground(Color.WHITE);
		emptyPanel.setBorder(new LineBorder(new Color(130, 135, 144)));
		rightSplitPane.addPane(emptyPanel);
		
		TaskListPanel taskListPanel = new TaskListPanel(dataContainer);
		taskListPanel.setEventBus(eventBus);
		rightSplitPane.addPane(taskListPanel);
	}

}
