package org.cdahmedeh.orgapp.swing.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.containers.ContextContainer;
import org.cdahmedeh.orgapp.swing.category.ContextListPanel;
import org.cdahmedeh.orgapp.types.category.Context;

public class MainSwingWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ContextContainer contextContainer = new ContextContainer();
		contextContainer.addContext(new Context("Essentials"));
		BigContainer bigContainer = new BigContainer(null, contextContainer, null);
		
		ContextListPanel contextListPanel = new ContextListPanel(bigContainer);
		frame.add(contextListPanel);
	}

}
