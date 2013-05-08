package org.cdahmedeh.orgapp.swingui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.table.DefaultTableCellRenderer;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.cdahmedeh.orgapp.types.time.TripleDurationInfo;
import org.joda.time.Duration;

/**
 * Renderer for TripleDurationInfo. Shows all three values and a dual-layer
 * progress bar. 
 * 
 * @author Ahmed El-Hajjar
 */
public class RoundedTripleDurationCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 7478775633982677184L;

	public RoundedTripleDurationCellRenderer() {
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
				FuzzyDateParser.durationToFuzzyString(((TripleDurationInfo)value).getEstimate()) +
				" ");
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
		
		g.setColor(new Color(0, 0, 0, 5));
		g.fillRoundRect(0, 1, this.getWidth(), this.getHeight()-3, 0, 0);
		
		g.setColor(brighter);
		g.fillRoundRect(0, 1, (int)(this.getWidth()*(secondProgress/totalProgress)), this.getHeight()-3, 0, 0);
		g.fillRoundRect(0, 1, (int)(this.getWidth()*(firstProgress/totalProgress)), this.getHeight()-3, 0, 0);
	}
}
