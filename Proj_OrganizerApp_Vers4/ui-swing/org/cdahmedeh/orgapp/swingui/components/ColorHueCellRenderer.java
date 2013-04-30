package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renders color based on hue component.
 *  
 * @author Ahmed El-Hajjar
 */
public class ColorHueCellRenderer extends DefaultTableCellRenderer {
	private static final float DEFAULT_SATURATION = 0.6f;
	private static final float DEFAULT_BRIGHTNESS = 0.8f;
	
	private static final long serialVersionUID = 8131845889651739436L;

	@Override
	public void paint(Graphics g) {
		float hue = Integer.parseInt(this.getText())/255f;
		
		Color color = new Color(Color.HSBtoRGB(hue, DEFAULT_SATURATION, DEFAULT_BRIGHTNESS));
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
