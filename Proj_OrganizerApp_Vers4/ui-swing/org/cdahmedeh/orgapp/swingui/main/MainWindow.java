package org.cdahmedeh.orgapp.swingui.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import org.cdahmedeh.orgapp.swingui.context.ContextListPanel;

import java.awt.BorderLayout;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ContextListPanel contextListPanel = new ContextListPanel();
		frame.getContentPane().add(contextListPanel, BorderLayout.WEST);
	}

}
