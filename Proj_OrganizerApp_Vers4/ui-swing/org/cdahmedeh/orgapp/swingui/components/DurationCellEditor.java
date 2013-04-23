package org.cdahmedeh.orgapp.swingui.components;

import javax.swing.JTextField;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Editor for dates. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DurationCellEditor extends AbstractParserEntryCellEditor<Duration>{
	private static final long serialVersionUID = 4137237283040982074L;

	public DurationCellEditor(JTextField editorTextField) {
		super(editorTextField);
	}

	@Override
	protected String reparse(Object value) {
		return FuzzyDateParser.durationToFuzzyString((Duration)value);
	}

	@Override
	protected Duration parse(String context) {
		return FuzzyDateParser.fuzzyStringToDuration(context);
	}

	@Override
	protected String preview(Duration dateValue) {
		return FuzzyDateParser.durationToFuzzyString(dateValue) + " hours";
	}

	@Override
	protected String getParseEmptyText() {
		return "0";
	}
	
}
