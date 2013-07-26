package com.tronicdream.epochdivider.swingui.components;

import javax.swing.table.DefaultTableCellRenderer;

import com.tronicdream.epochdivider.core.parsers.FuzzyDateParser;

import org.joda.time.DateTime;

public class DateEntryCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 7478775633982677184L;

	protected void setValue(Object value) {
		if (value instanceof DateTime){
			super.setValue(value);
			setText(FuzzyDateParser.dateTimeToFuzzyString(((DateTime)value)));
		} else if (value instanceof String){
			super.setValue(value);
			setText((String) value);
		} else if (value == null){
			super.setValue(null);
			setText("");
		}
	}
}
