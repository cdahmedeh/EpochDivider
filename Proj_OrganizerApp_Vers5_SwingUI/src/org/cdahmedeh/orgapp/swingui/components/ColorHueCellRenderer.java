package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.table.DefaultTableCellRenderer;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;

/**
 * Renders color based on hue component.
 *  
 * @author Ahmed El-Hajjar
 */
public class ColorHueCellRenderer extends DefaultTableCellRenderer {
	private static final float DEFAULT_SATURATION = 0.6f;
	private static final float DEFAULT_BRIGHTNESS = 0.8f;
	private static final int DEFAULT_BORDER = 2;
	private static final int DEFAULT_ROUNDESS = 5;
	
	private static final long serialVersionUID = 8131845889651739436L;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		GraphicsHelper.enableDefaultAASettings(g);
		
		float hue = (Integer)value/255f;
		
		Color color = new Color(Color.HSBtoRGB(hue, DEFAULT_SATURATION, DEFAULT_BRIGHTNESS));
		g.setColor(color);
		g.fillRoundRect(DEFAULT_BORDER, DEFAULT_BORDER, this.getWidth()-DEFAULT_BORDER*2, this.getHeight()-1-DEFAULT_BORDER*2, DEFAULT_ROUNDESS, DEFAULT_ROUNDESS);
	}
	
	Object value = "";
	
	@Override
	protected void setValue(Object value) {
		this.value = value;
		this.setText("");
//		super.setValue(value);
	}
	
	@Override
	public String getText() {
		return "";
	}
	
}
