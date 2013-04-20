package org.cdahmedeh.orgapp.swingui.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.swingui.context.ContextListPanel;
import org.cdahmedeh.orgapp.swingui.notification.LoadContextListPanelRequest;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

import java.awt.BorderLayout;

public class MainWindow {
	// - Events -
	public EventBus eventBus;
	
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

		ContextListPanel contextListPanel = new ContextListPanel(dataContainer);
		contextListPanel.setEventBus(eventBus);
		frame.getContentPane().add(contextListPanel, BorderLayout.WEST);
	}

}
