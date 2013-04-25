package org.cdahmedeh.orgapp.swingui.main;

import java.awt.BorderLayout;
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
import org.cdahmedeh.orgapp.swingui.notification.WindowLoadedNotification;
import org.cdahmedeh.orgapp.swingui.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;

import com.google.common.eventbus.EventBus;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

public class MainWindow {
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
	
	// - Events and Logging -
	private EventBus eventBus;
	private Logger logger = Logger.getLogger("org.cdahmedeh.orgapp.log");
	
	// - Components -
	private JFrame frame;

	// - Data -
	private DataContainer dataContainer;
	
	public MainWindow() {
		//Prepare logger
		BasicConfigurator.configure();
		logger.setLevel(Level.INFO);
		logger.info("Epoch Divider has started!");
		
		//Set look and feel
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		logger.info("Configured Look and Feel");
		
		//Prepare some test data, and prepare eventBus.
		dataContainer = TestDataGenerator.generateDataContainer();
//		dataContainer = TestDataGenerator.generateDataContainerWithLotsOfData();
		eventBus = new EventBus();
		logger.info("Test Data Generated");
		
		//Show the application window
		initialize();
		this.frame.setVisible(true);
		logger.info("Window initialized");
		
		//Let components do post-window load tasks.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				eventBus.post(new WindowLoadedNotification());
				eventBus.post(new LoadContextListPanelRequest());
			}
		});
		logger.info("Post-load events sent");
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle(UIConstants.WINDOW_TITLE);

		frame.setBounds(UIConstants.DEFAULT_WINDOW_XPOS, UIConstants.DEFAULT_WINDOW_YPOS, UIConstants.DEFAULT_WINDOW_WIDTH, UIConstants.DEFAULT_WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Split pane
		JXMultiSplitPane mainSplitPane = new JXMultiSplitPane();
		mainSplitPane.setModel(new DefaultSplitPaneModel());
		mainSplitPane.setContinuousLayout(true);
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
		
		//Context list
		ContextListPanel contextListPanel = new ContextListPanel(dataContainer, eventBus);
		mainSplitPane.add(contextListPanel, DefaultSplitPaneModel.LEFT);
		
		//Calendar 
		CalendarPanel calendarPanel = new CalendarPanel(dataContainer, eventBus);
		mainSplitPane.add(calendarPanel, DefaultSplitPaneModel.TOP);
		
		//Task list
		TaskListPanel taskListPanel = new TaskListPanel(dataContainer, eventBus);
		mainSplitPane.add(taskListPanel, DefaultSplitPaneModel.BOTTOM);
	}

}
