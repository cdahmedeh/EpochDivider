package org.cdahmedeh.orgapp.swingui.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	private PersistanceManagerInterface pm;
	private File currentFile = null;
	
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

		//Load some data
		pm = new SQLitePersistenceManager();
		dataContainer = new DataContainer();
		dataContainer.generateDefaults();
		eventBus = new EventBus();
		logger.info("Data Loaded");
		
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
		mainSplitPane.setBorder(new EmptyBorder(UIConstants.DEFAULT_PANEL_MARGIN_WIDTH-1, UIConstants.DEFAULT_PANEL_MARGIN_WIDTH, UIConstants.DEFAULT_PANEL_MARGIN_WIDTH, UIConstants.DEFAULT_PANEL_MARGIN_WIDTH));
		mainSplitPane.setDividerSize(UIConstants.DEFAULT_PANEL_MARGIN_WIDTH);
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
		menuBar.add(mnDebug);
		
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

	// - Non-sequential methods -
	private void updateDataContainer(DataContainer dataContainer){
		this.dataContainer.replace(dataContainer);
		
		eventBus.post(new ContextsChangedNotification());
		eventBus.post(new TasksChangedNotification());
	}
}
