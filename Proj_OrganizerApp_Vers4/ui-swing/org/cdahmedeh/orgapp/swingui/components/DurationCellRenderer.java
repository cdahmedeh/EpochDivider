package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.table.DefaultTableCellRenderer;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.joda.time.Duration;

public class DurationCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 7478775633982677184L;

	public DurationCellRenderer() {
		super();
		super.setHorizontalAlignment(RIGHT);
	}
	
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
		
		Color brighter = new Color(0, 0, 0, 30);
		
		g.setColor(brighter);
		g.fillRect(0, 0, (int)(this.getWidth()*(secondProgress/totalProgress)), this.getHeight());
		
		g.fillRect(0, 0, (int)(this.getWidth()*(firstProgress/totalProgress)), this.getHeight());
	}
	
	@Override
	public void setText(String text) {
		switch(new Random().nextInt(3)){
			case 0: super.setText("2.3 / 5.0 / " + text); break;
			case 1: super.setText("1.5 / 12.0 / " + text); break;
			case 2: super.setText("5.55 / 100.0 / " + text); break;
		}
//		super.setText(text);
	}
}
