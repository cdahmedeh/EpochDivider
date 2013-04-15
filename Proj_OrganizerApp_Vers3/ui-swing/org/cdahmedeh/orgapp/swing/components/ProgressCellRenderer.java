package org.cdahmedeh.orgapp.swing.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.table.DefaultTableCellRenderer;

import org.cdahmedeh.orgapp.swing.category.ProgressInfo;
import org.cdahmedeh.orgapp.swing.helpers.GraphicsHelper;


public class ProgressCellRenderer extends DefaultTableCellRenderer {
	private ProgressInfo savedValue;

	@Override
	protected void setValue(Object value) {
		this.savedValue = (ProgressInfo)value;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g); //TODO: Is text still behind it?
		
		GraphicsHelper.enableAntiAliasing(g);
        
		double start = savedValue.first;
		double start2 = savedValue.second;
//		double start2 = 100;
		double total = savedValue.third;

		g.setColor(new Color(Color.HSBtoRGB(savedValue.color/255f, 1f, 0.7f)));
		g.fillRoundRect(1, 1, this.getBounds().width-3, this.getBounds().height-2, 10, 10);
		g.setColor(new Color(Color.HSBtoRGB(savedValue.color/255f, 0.8f, 0.9f)));
		g.fillRoundRect(1, 1, Math.min(this.getBounds().width-3, (int)(((start2/total))*this.getBounds().width)-3), this.getBounds().height-3, 10, 10);
		g.setColor(new Color(Color.HSBtoRGB(savedValue.color/255f, 0.5f, 1f)));
		g.fillRoundRect(1, 1, Math.min(this.getBounds().width-3, (int)(((start/total))*this.getBounds().width)-3), this.getBounds().height-3, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(1, 1, this.getBounds().width-3, this.getBounds().height-3, 10, 10);

		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(start), 5, this.getBounds().height-3);
	
		int stringWidth = g.getFontMetrics().stringWidth(String.valueOf(total));
		
		int stringWidth2 = g.getFontMetrics().stringWidth(String.valueOf(start2));

		g.drawString(String.valueOf(total), this.getBounds().width - stringWidth-5, this.getBounds().height-3);
		g.drawString(String.valueOf(start2), this.getBounds().width/2 - stringWidth2/2, this.getBounds().height-3);

		
		
	}
}