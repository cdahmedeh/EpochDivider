package com.tronicdream.epochdivider.swingui.helpers;

import java.awt.Component;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * Various methods to help reduce boilerplate code when creating toolbar buttons
 * and other toolbar elements. 
 * 
 * @author Ahmed El-Hajjar
 */
public class ToolbarHelper {
	public static Component createToolbarHorizontalGlue(JToolBar toolbar){
		Component horizontalGlue = Box.createHorizontalGlue();
		toolbar.add(horizontalGlue);
		return horizontalGlue;
	}
	
	public static Component createToolbarSeperator(JToolBar toolbar) {
		Component seperator = new JToolBar.Separator();
		toolbar.add(seperator);
		return seperator;		
	}
	
	public static Component createToolbarInvisibleSeperator(JToolBar toolbar) {
		Component seperator = Box.createHorizontalStrut(5);
		toolbar.add(seperator);
		return seperator;		
	}
	
	public static JButton createToolbarButton(JToolBar toolbar, String label, URL iconURL){
		JButton toolbarButton = new JButton(label);
		toolbarButton.setIcon(new ImageIcon(iconURL));
		toolbarButton.setFocusable(false);
		toolbar.add(toolbarButton);
		return toolbarButton;
	}

	public static JToggleButton createToolbarToggleButton(JToolBar toolbar, String label, URL iconURL){
		JToggleButton toolbarToggleButton = new JToggleButton(label);
		toolbarToggleButton.setIcon(new ImageIcon(iconURL));
		toolbarToggleButton.setFocusable(false);
		toolbar.add(toolbarToggleButton);
		return toolbarToggleButton;
	}
	
	public static JRadioButton createToolbarRadio(JToolBar toolbar, String label){
		JRadioButton toolbarRadioButton = new JRadioButton(label);
		toolbarRadioButton.setFocusable(false);
		toolbar.add(toolbarRadioButton);
		return toolbarRadioButton;
	}
}
