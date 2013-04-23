package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.table.DefaultTableCellRenderer;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.joda.time.Duration;

public class DurationCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 7478775633982677184L;

	protected void setValue(Object value) {
		if (value instanceof Duration){
			super.setValue(value);
			setText(FuzzyDateParser.durationToFuzzyString(((Duration)value)));
		} else if (value instanceof String){
			super.setValue(value);
			setText((String) value);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		double totalProgress = 10.0;
		double secondProgress = new Random().nextDouble()*totalProgress;
		double firstProgress = new Random().nextDouble()*secondProgress;
		
		Color brighter = new Color(0, 0, 0, 50);
		
		g.setColor(brighter);
		g.fillRect(0, 0, (int)(this.getWidth()*(secondProgress/totalProgress)), this.getHeight());
		
		g.fillRect(0, 0, (int)(this.getWidth()*(firstProgress/totalProgress)), this.getHeight());
	}
}
