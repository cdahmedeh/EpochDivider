package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.table.DefaultTableCellRenderer;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.cdahmedeh.orgapp.types.task.TripleDurationInfo;
import org.joda.time.Duration;

public class DurationCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 7478775633982677184L;

	public DurationCellRenderer() {
		super();
		super.setHorizontalAlignment(RIGHT);
	}
	
	TripleDurationInfo value = new TripleDurationInfo(Duration.ZERO, Duration.ZERO, Duration.ZERO);
	
	protected void setValue(Object value) {
		this.value = (TripleDurationInfo)value;
		
		if (value instanceof TripleDurationInfo){
			setText(
				FuzzyDateParser.durationToFuzzyString(((TripleDurationInfo)value).getTotalPassed()) +
				" / " +
				FuzzyDateParser.durationToFuzzyString(((TripleDurationInfo)value).getTotalScheduled()) +
				" / " +
				FuzzyDateParser.durationToFuzzyString(((TripleDurationInfo)value).getEstimate())
				);
		} else if (value instanceof String){
			super.setValue(value);
			setText((String) value);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		double totalProgress = value.getEstimate().getStandardSeconds();
		double secondProgress = value.getTotalScheduled().getStandardSeconds();
		double firstProgress = value.getTotalPassed().getStandardSeconds();
		
		Color brighter = new Color(0, 0, 0, 20);
		
		g.setColor(brighter);
		g.fillRect(0, 0, (int)(this.getWidth()*(secondProgress/totalProgress)), this.getHeight());
		g.fillRect(0, 0, (int)(this.getWidth()*(firstProgress/totalProgress)), this.getHeight());
	}
}
