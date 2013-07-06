package org.cdahmedeh.orgapp.swingui.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.pers.PersistanceManagerInterface;
import org.cdahmedeh.orgapp.pers.SQLitePersistenceManager;
import org.cdahmedeh.orgapp.swingui.calendar.CalendarPanel;
import org.cdahmedeh.orgapp.swingui.context.ContextListPanel;
import org.cdahmedeh.orgapp.swingui.notification.ContextsChangedNotification;
import org.cdahmedeh.orgapp.swingui.notification.LoadContextListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.TasksChangedNotification;
import org.cdahmedeh.orgapp.swingui.notification.WindowLoadedNotification;
import org.cdahmedeh.orgapp.swingui.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;

import com.google.common.eventbus.EventBus;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSplitButton;
import com.jidesoft.swing.JideTabbedPane;

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
	
	// - Data -
	private DataContainer dataContainer;
	private PersistanceManagerInterface pm;
	private File currentFile = null;

	// - Components -
	private JFrame frame;
	private JideTabbedPane jideTabbedPane;

	// - Sequential Methods - //
	
	public MainWindow() {
		//Prepare logger
		prepareLogger();
		
		//Set look and feel
		prepareLookAndFeel();

		//Load some data
		preparePersistenceWithDefaults();
		
		//Setup EventBus
		prepareEventHandling();
		
		//Show the application window
		createMainWindow();
		createEventsTab();
		createOtherTabs();
		createMenu();
		showMainWindow();
		
		//Let components do post-window load tasks.
		notifyToPostLoad();
	}
	
	private void prepareLogger() {
		BasicConfigurator.configure();
		logger.setLevel(Level.DEBUG);
		logger.info("Logger configured to level: " + logger.getLevel());
	}
	
	private void prepareLookAndFeel() {
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
	        LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE_WITHOUT_MENU);
		} catch (UnsupportedLookAndFeelException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Look and Feel configured successfully");
	}
	
	private void preparePersistenceWithDefaults() {
		pm = new SQLitePersistenceManager();
		logger.info("Persistence Manager prepared");
		dataContainer = new DataContainer();
		dataContainer.generateDefaults();
		logger.info("Default Data generated");
	}
	
	private void prepareEventHandling() {
		eventBus = new EventBus();
		logger.info("EventBus instantiated");
	}
	
	private void notifyToPostLoad() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				eventBus.post(new WindowLoadedNotification());
				eventBus.post(new LoadContextListPanelRequest());
			}
		});
		logger.info("Post-load request event sent");
	}

	private void createMainWindow() {
		frame = new JFrame();
		frame.setTitle(UIConstants.WINDOW_TITLE);

		frame.setBounds(UIConstants.DEFAULT_WINDOW_XPOS, UIConstants.DEFAULT_WINDOW_YPOS, UIConstants.DEFAULT_WINDOW_WIDTH, UIConstants.DEFAULT_WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jideTabbedPane = new JideTabbedPane();
		jideTabbedPane.setColorTheme(JideTabbedPane.COLOR_THEME_WIN2K);
		jideTabbedPane.setContentBorderInsets(new Insets(1, 0, 0, 0));
		frame.getContentPane().add(jideTabbedPane, BorderLayout.CENTER);
		jideTabbedPane.setTabShape(JideTabbedPane.SHAPE_FLAT);
	}

	private void createEventsTab() {
		//Split pane
		JXMultiSplitPane mainSplitPane = new JXMultiSplitPane();
		mainSplitPane.setBorder(new EmptyBorder(UIConstants.DEFAULT_PANEL_MARGIN_WIDTH, UIConstants.DEFAULT_PANEL_MARGIN_WIDTH, UIConstants.DEFAULT_PANEL_MARGIN_WIDTH, UIConstants.DEFAULT_PANEL_MARGIN_WIDTH));
		mainSplitPane.setDividerSize(UIConstants.DEFAULT_PANEL_MARGIN_WIDTH);
		mainSplitPane.setModel(new DefaultSplitPaneModel());
		mainSplitPane.setContinuousLayout(true);
		jideTabbedPane.addTab("Tasks", new ImageIcon(MainWindow.class.getResource("/org/cdahmedeh/orgapp/imt/icons/tasks.gif")), mainSplitPane);
		
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
	
	private void createOtherTabs() {
		jideTabbedPane.addTab("Events", new ImageIcon(MainWindow.class.getResource("/org/cdahmedeh/orgapp/imt/icons/events.gif")), new JPanel());
		jideTabbedPane.addTab("Reminders", new ImageIcon(MainWindow.class.getResource("/org/cdahmedeh/orgapp/imt/icons/week.gif")), new JPanel());
		jideTabbedPane.addTab("Statistics", new ImageIcon(MainWindow.class.getResource("/org/cdahmedeh/orgapp/imt/icons/statistic.gif")), new JPanel());
	}
	
	private void createMenu() {
		JideSplitButton menuButton = new JideSplitButton("Menu");
		menuButton.setAlwaysDropdown(true);
		jideTabbedPane.setTabLeadingComponent(menuButton);
		
		//		frame.setJMenuBar(menuBar);
		
//		splitButton.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuButton.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setIcon(new ImageIcon(MainWindow.class.getResource("/org/cdahmedeh/orgapp/imt/icons/new.gif")));
		mnFile.add(mntmNew);
		
		mntmNew.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
			DataContainer dataContainer2 = new DataContainer();
			dataContainer2.generateDefaults();
			updateDataContainer(dataContainer2);
		}});
		
		mnFile.addSeparator();
		
		/* Load */
		JMenuItem mntmLoad = new JMenuItem("Open");
		mntmLoad.setIcon(new ImageIcon(MainWindow.class.getResource("/org/cdahmedeh/orgapp/imt/icons/open.gif")));
		mnFile.add(mntmLoad);
		
		mntmLoad.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
			File saveLocation = openFileDialog();
			if (saveLocation != null){
				updateDataContainer(pm.loadDataContainer(saveLocation));
				dataContainer.setView(TestDataGenerator.generateDataContainer().getView());
			}
		}});
		
		/* Save */
		mnFile.addSeparator();
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setIcon(new ImageIcon(MainWindow.class.getResource("/org/cdahmedeh/orgapp/imt/icons/save.gif")));
		mnFile.add(mntmSave);
		
		mntmSave.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
			
			if (currentFile != null){
				 pm.saveDataContainer(currentFile, dataContainer);
			} else {
				File saveLocation = openFileDialog();
				if (saveLocation != null){
					pm.saveDataContainer(saveLocation, dataContainer);
				}
			}
		}});

		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setIcon(new ImageIcon(MainWindow.class.getResource("/org/cdahmedeh/orgapp/imt/icons/saveas.gif")));
		mnFile.add(mntmSaveAs);
		
		mntmSaveAs.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
			File saveLocation = openFileDialog();
			if (saveLocation != null){
				pm.saveDataContainer(saveLocation, dataContainer);
			}
		}});
		
		/* Test Data */
		
		JMenu mnDebug = new JMenu("Debug");
		menuButton.add(mnDebug);
		
		JMenu mnSwitchTestData = new JMenu("Switch Test Data");
		mnDebug.add(mnSwitchTestData);
		
		JMenuItem mntmNormalTestData = new JMenuItem("Normal Test Data");
		mnSwitchTestData.add(mntmNormalTestData);
		
		JMenuItem mntmTestDataWith = new JMenuItem("Test Data with Lots of Information");
		mnSwitchTestData.add(mntmTestDataWith);
		
		JMenuItem mntmTestDataMuch = new JMenuItem("Test Data with too much Information");
		mnSwitchTestData.add(mntmTestDataMuch);
		
		JMenuItem mntmTestDataMuchSpread = new JMenuItem("Test Data with too much Information Spread Out");
		mnSwitchTestData.add(mntmTestDataMuchSpread);
		
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
		
		mntmTestDataMuchSpread.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
				updateDataContainer(TestDataGenerator.generateDataContainerWithTooMuchDataSpreadOut());}});
		
		mntmTestDataFor.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
				updateDataContainer(TestDataGenerator.generateDataContainerForStressingCalendarPainter());}});
		
		mntmTestDataAhmed.addActionListener(new ActionListener() {@Override	public void actionPerformed(ActionEvent e) {
				updateDataContainer(TestDataGenerator.generateDataContainerWithAhmedsData());}});
		
		/* End Test Data */
	}
	
	private void showMainWindow() {
		this.frame.setVisible(true);
		logger.info("Window initialized");
	}
	
	// - Non-sequential methods - //
	
	private File openFileDialog() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
		jFileChooser.setCurrentDirectory(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toString()));
		int openDialogReturn = jFileChooser.showOpenDialog(frame.getContentPane());
		if (openDialogReturn == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jFileChooser.getSelectedFile();
			this.currentFile = selectedFile; 
			this.frame.setTitle(UIConstants.WINDOW_TITLE + " - " + currentFile.getName());
			return selectedFile;
		}
		return null;
	}

	private void updateDataContainer(DataContainer dataContainer){
		this.dataContainer.replace(dataContainer);
		
		eventBus.post(new ContextsChangedNotification());
		eventBus.post(new TasksChangedNotification());
	}
}
