package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.table.DefaultTableCellRenderer;

public class ColorHueCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 8131845889651739436L;

	@Override
	public void paint(Graphics g) {
//		super.paint(g);
		
		float hue = Integer.parseInt(this.getText())/255f;
		
		Color color = new Color(Color.HSBtoRGB(hue, 0.6f, 0.8f));
//		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));		
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
