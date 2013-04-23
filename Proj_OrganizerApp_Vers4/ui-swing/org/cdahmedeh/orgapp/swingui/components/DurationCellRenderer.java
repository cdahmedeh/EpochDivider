package org.cdahmedeh.orgapp.swingui.components;

import javax.swing.table.DefaultTableCellRenderer;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.joda.time.DateTime;
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
}
