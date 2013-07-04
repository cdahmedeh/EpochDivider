package org.cdahmedeh.orgapp.swingui.main;

import java.awt.EventQueue;

import javax.swing.JFrame;

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

	private JFrame frame;
	
	/**
	 * Create the application.
	 */
	public MainSwingWindow() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds(
				MainSwingWindowConstants.MAIN_WINDOW_DEFAULT_X_POS, 
				MainSwingWindowConstants.MAIN_WINDOW_DEFAULT_Y_POS, 
				MainSwingWindowConstants.MAIN_WINDOW_DEFAULT_WIDTH, 
				MainSwingWindowConstants.MAIN_WINDOW_DEFAULT_HEIGHT
		);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
