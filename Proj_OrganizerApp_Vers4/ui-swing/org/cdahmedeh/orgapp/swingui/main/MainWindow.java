package org.cdahmedeh.orgapp.swingui.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.swingui.calendar.CalendarPanel;
import org.cdahmedeh.orgapp.swingui.context.ContextListPanel;
import org.cdahmedeh.orgapp.swingui.notification.LoadContextListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.LoadTaskListPanelRequest;
import org.cdahmedeh.orgapp.swingui.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;

import com.google.common.eventbus.EventBus;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

import java.awt.BorderLayout;

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

		JXMultiSplitPane mainSplitPane = new JXMultiSplitPane();
		mainSplitPane.setModel(new DefaultSplitPaneModel());
		mainSplitPane.setContinuousLayout(true);
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
		
		ContextListPanel contextListPanel = new ContextListPanel(dataContainer);
		contextListPanel.setEventBus(eventBus);
		mainSplitPane.add(contextListPanel, DefaultSplitPaneModel.LEFT);
		
		CalendarPanel emptyPanel = new CalendarPanel(dataContainer);
		emptyPanel.setEventBus(eventBus);
		mainSplitPane.add(emptyPanel, DefaultSplitPaneModel.TOP);
		
		TaskListPanel taskListPanel = new TaskListPanel(dataContainer);
		taskListPanel.setEventBus(eventBus);
		mainSplitPane.add(taskListPanel, DefaultSplitPaneModel.BOTTOM);
	}

}
