package org.cdahmedeh.orgapp.swing.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.table.DefaultTableCellRenderer;

import org.cdahmedeh.orgapp.swing.helpers.GraphicsHelper;


public class ProgressCellRenderer extends DefaultTableCellRenderer {
	private ProgressInfo savedValue;

	@Override
	protected void setValue(Object value) {
		this.savedValue = (ProgressInfo)value;
	}

	@Override
	public void paint(Graphics g) {
//		super.paint(g); //TODO: Is text still behind it?
		
		GraphicsHelper.enableAntiAliasing(g);
        
        Graphics2D g2d = (Graphics2D) g;
		
		double start = savedValue.first;
		double start2 = savedValue.second;
		double total = savedValue.third;

		GradientPaint gp = new GradientPaint(0, 0, new Color(Color.HSBtoRGB(savedValue.color/255f, (-28+30)/255f, (0+252)/255f)), 0, 15, new Color(Color.HSBtoRGB(savedValue.color/255f, (-28+60)/255f, (0+252)/255f)), true);
		g2d.setPaint(gp);
		g.fillRoundRect(1, 1, this.getBounds().width-3, this.getBounds().height-2, 10, 10);
		gp = new GradientPaint(0, 0, new Color(Color.HSBtoRGB(savedValue.color/255f, (60+30)/255f, (-10+252)/255f)), 0, 15, new Color(Color.HSBtoRGB(savedValue.color/255f, (60+60)/255f, (-10+252)/255f)), true);
		g2d.setPaint(gp);
		g.fillRoundRect(1, 1, Math.min(this.getBounds().width-3, (int)(((start2/total))*this.getBounds().width)-3), this.getBounds().height-3, 10, 10);
		gp = new GradientPaint(0, 0, new Color(Color.HSBtoRGB(savedValue.color/255f, (140+30)/255f, (-10+252)/255f)), 0, 15, new Color(Color.HSBtoRGB(savedValue.color/255f, (140+60)/255f, (-10+252)/255f)), true);
		g2d.setPaint(gp);
		g.fillRoundRect(1, 1, Math.min(this.getBounds().width-3, (int)(((start/total))*this.getBounds().width)-3), this.getBounds().height-3, 10, 10);
//		g.setColor(new Color(Color.HSBtoRGB(savedValue.color/255f, 18/255f, 253/255f)));
//		g.setColor(Color.BLACK);
//		g.drawRoundRect(2, 2, this.getBounds().width-5, this.getBounds().height-5, 10, 10);
		g.setColor(new Color(Color.HSBtoRGB(savedValue.color/255f, 100/255f, 206/255f)));
		g.drawRoundRect(1, 1, this.getBounds().width-3, this.getBounds().height-3, 10, 10);
		g.drawLine((int)(1/3f * this.getBounds().width), 3, (int) (1/3f * this.getBounds().width), this.getBounds().height-4);
		g.drawLine((int)(2/3f * this.getBounds().width), 3, (int) (2/3f * this.getBounds().width), this.getBounds().height-4);
		
		g.setColor(Color.BLACK);
	
		int stringWidth = g.getFontMetrics().stringWidth(String.valueOf(total));
		
		int stringWidth2 = g.getFontMetrics().stringWidth(String.valueOf(start2));
		
		int heightof = g.getFontMetrics().getHeight()/2-3;

		g.drawString(String.valueOf(start), 5, this.getBounds().height/2+heightof);
		g.drawString(String.valueOf(total), this.getBounds().width - stringWidth-5, this.getBounds().height/2+heightof);
		g.drawString(String.valueOf(start2), this.getBounds().width/2 - stringWidth2/2, this.getBounds().height/2+heightof);

		
		
	}
}