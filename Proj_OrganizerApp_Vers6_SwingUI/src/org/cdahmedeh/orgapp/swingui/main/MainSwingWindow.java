package org.cdahmedeh.orgapp.swingui.main;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cdahmedeh.orgapp.generators.DataContainerGenerator;
import org.cdahmedeh.orgapp.swingui.contextlist.ContextListPanel;
import org.cdahmedeh.orgapp.types.containers.DataContainer;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;

import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.jidesoft.plaf.LookAndFeelFactory;

public class MainSwingWindow {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainSwingWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* --- Non-static Section --- */
	
	//Logger
	private Logger logger = Logger.getLogger("org.cdahmedeh.orgapp.log");
	
	//Data Instance
	private DataContainer dataContainer;

	//UI Components
	private JFrame frame;
	
	public MainSwingWindow() {
		prepareLogger();
		loadData();
		configureLookAndFeel();
		createUIComponents();
		displayWindow();
	}

	/* --- UI Steps --- */
	
	private void prepareLogger() {
		BasicConfigurator.configure();
		logger.setLevel(Level.INFO);
		logger.info("Logger Prepared");
	}

	private void loadData() {
		dataContainer = DataContainerGenerator.generateBasicDataContainer();
		logger.info("Test Data generated");
	}

	private void configureLookAndFeel() {
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
	        LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE_WITHOUT_MENU);
			logger.info("Look and Feel setup complete");
		} catch (UnsupportedLookAndFeelException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void createUIComponents() {
		//Main frame basic preparations
		frame = new JFrame();
		frame.setBounds(
				MainSwingWindowConstants.MAIN_WINDOW_DEFAULT_X_POS, MainSwingWindowConstants.MAIN_WINDOW_DEFAULT_Y_POS,
				MainSwingWindowConstants.MAIN_WINDOW_DEFAULT_WIDTH,	MainSwingWindowConstants.MAIN_WINDOW_DEFAULT_HEIGHT
		);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 1));
		
		// Main Split Pane
		JXMultiSplitPane mainMultiSplitPane = new JXMultiSplitPane();
		mainMultiSplitPane.setModel(new DefaultSplitPaneModel());
		frame.getContentPane().add(mainMultiSplitPane);
		
		// Context List Panel
		ContextListPanel contextListPanel = new ContextListPanel(dataContainer);
		mainMultiSplitPane.add(contextListPanel, DefaultSplitPaneModel.LEFT);
	}

	private void displayWindow() {
		frame.setVisible(true);
		logger.info("Shown main window");
	}
}
