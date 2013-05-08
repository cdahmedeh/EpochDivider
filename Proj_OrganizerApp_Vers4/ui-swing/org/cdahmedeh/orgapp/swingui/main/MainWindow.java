package org.cdahmedeh.orgapp.swingui.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.pers.PersistenceManager;
import org.cdahmedeh.orgapp.swingui.calendar.CalendarPanel;
import org.cdahmedeh.orgapp.swingui.context.ContextListPanel;
import org.cdahmedeh.orgapp.swingui.notification.LoadContextListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
import org.cdahmedeh.orgapp.swingui.notification.RefreshTaskListRequest;
import org.cdahmedeh.orgapp.swingui.notification.TasksChangedNotification;
import org.cdahmedeh.orgapp.swingui.notification.WindowLoadedNotification;
import org.cdahmedeh.orgapp.swingui.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;

import com.google.common.eventbus.EventBus;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;

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
	        LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE_WITHOUT_MENU);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		logger.info("Configured Look and Feel");
		
		//Prepare some test data, and prepare eventBus.
		dataContainer = TestDataGenerator.generateDataContainer();
//		dataContainer = PersistenceManager.loadDataContainer();
//		dataContainer.setView(TestDataGenerator.generateDataContainer().getView());
		eventBus = new EventBus();
		logger.info("Test Data Generated");
		
		//Show the application window
		initialize();
		createMenu();
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
		mainSplitPane.setBorder(new EmptyBorder(2, 3, 3, 3));
		mainSplitPane.setDividerSize(3);
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
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		/* Test Data */
		
		JMenu mnSwitchTestData = new JMenu("Switch Test Data");
		mnFile.add(mnSwitchTestData);
		
		JMenuItem mntmNormalTestData = new JMenuItem("Normal Test Data");
		mnSwitchTestData.add(mntmNormalTestData);
		
		JMenuItem mntmTestDataWith = new JMenuItem("Test Data with Lots of Information");
		mnSwitchTestData.add(mntmTestDataWith);
		
		JMenuItem mntmTestDataMuch = new JMenuItem("Test Data with too much Information");
		mnSwitchTestData.add(mntmTestDataMuch);
		
		JMenuItem mntmTestDataFor = new JMenuItem("Test Data For Stressing Calendar Renderer");
		mnSwitchTestData.add(mntmTestDataFor);
		
		JMenuItem mntmTestDataAhmed = new JMenuItem("Test Data with Ahmed's Data");
		mnSwitchTestData.add(mntmTestDataAhmed);
		
		mntmNormalTestData.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
				updateDataContainer(TestDataGenerator.generateDataContainer());}});
		
		mntmTestDataWith.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
				updateDataContainer(TestDataGenerator.generateDataContainerWithLotsOfData());}});
		
		mntmTestDataMuch.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
				updateDataContainer(TestDataGenerator.generateDataContainerWithTooMuchData());}});
		
		mntmTestDataFor.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
				updateDataContainer(TestDataGenerator.generateDataContainerForStressingCalendarPainter());}});
		
		mntmTestDataAhmed.addActionListener(new ActionListener() {@Override	public void actionPerformed(ActionEvent e) {
				updateDataContainer(TestDataGenerator.generateDataContainerWithAhmedsData());}});
		
		/* End Test Data */
	}
	
	private void updateDataContainer(DataContainer dataContainer){
		this.dataContainer.replace(dataContainer);
		
		eventBus.post(new RefreshContextListRequest());
		eventBus.post(new TasksChangedNotification());
		eventBus.post(new RefreshTaskListRequest());
	}
}
