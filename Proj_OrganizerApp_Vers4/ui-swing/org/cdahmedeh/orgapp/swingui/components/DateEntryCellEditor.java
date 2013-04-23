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
		return dateValue.toString("EEEE, MMMM d, YYYY 'at' HH:mm");
	}

	@Override
	protected String getParseEmptyText() {
		return "Never";
	}
	
}
