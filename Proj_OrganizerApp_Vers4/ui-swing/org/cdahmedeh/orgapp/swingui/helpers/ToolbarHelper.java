package org.cdahmedeh.orgapp.swingui.helpers;

import java.awt.Component;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class ToolbarHelper {
	public static Component createToolbarHorizontalGlue(JToolBar toolbar){
		Component horizontalGlue = Box.createHorizontalGlue();
		toolbar.add(horizontalGlue);
		return horizontalGlue;
	}
	
	public static JButton createToolbarButton(JToolBar toolbar, String label ,URL iconURL){
		JButton toolbarButton = new JButton(label);
		toolbarButton.setIcon(new ImageIcon(iconURL));
		toolbar.add(toolbarButton);
		return toolbarButton;
	}

	public static JToggleButton createToolbarToggleButton(JToolBar toolbar, String label ,URL iconURL){
		JToggleButton toolbarToggleButton = new JToggleButton(label);
		toolbarToggleButton.setIcon(new ImageIcon(iconURL));
		toolbar.add(toolbarToggleButton);
		return toolbarToggleButton;
	}
	
	public static Component createToolbarSeperator(JToolBar toolbar) {
		Component seperator = new JToolBar.Separator();
		toolbar.add(seperator);
		return seperator;		
	}
}
