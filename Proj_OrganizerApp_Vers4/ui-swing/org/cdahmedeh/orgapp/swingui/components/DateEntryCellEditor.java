package org.cdahmedeh.orgapp.swingui.components;

import javax.swing.JTextField;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.joda.time.DateTime;

/**
 * Editor for dates. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DateEntryCellEditor extends AbstractParserEntryCellEditor<DateTime>{
	private static final String DEFAULT_NONE_STRING = "Never";
	private static final String DEFAULT_DATE_FORMAT = "EEEE, MMMM d, YYYY 'at' HH:mm";
	
	private static final long serialVersionUID = 4137237283040982074L;

	public DateEntryCellEditor(JTextField editorTextField) {
		super(editorTextField);
	}

	@Override
	protected String parseTypeToString(Object value) {
		return FuzzyDateParser.dateTimeToFuzzyString((DateTime)value);
	}

	@Override
	protected DateTime parseStringToType(String context) {
		return FuzzyDateParser.fuzzyStringToDateTime(context);
	}

	@Override
	protected String previewParseToString(DateTime dateValue) {
		return dateValue.toString(DEFAULT_DATE_FORMAT);
	}

	@Override
	protected String getParseEmptyText() {
		return DEFAULT_NONE_STRING;
	}
	
}
