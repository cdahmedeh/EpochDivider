package org.cdahmedeh.orgapp.swingui.components;

import javax.swing.JTextField;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.cdahmedeh.orgapp.types.task.TripleDurationInfo;

/**
 * Editor for dates. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DurationCellEditor extends AbstractParserEntryCellEditor<TripleDurationInfo>{
	private static final long serialVersionUID = 4137237283040982074L;

	public DurationCellEditor(JTextField editorTextField) {
		super(editorTextField);
	}

	@Override
	protected String parseTypeToString(Object value) {
		return FuzzyDateParser.durationToFuzzyString(((TripleDurationInfo)value).getEstimate());
	}

	@Override
	protected TripleDurationInfo parseStringToType(String context) {
		return new TripleDurationInfo(null, null, FuzzyDateParser.fuzzyStringToDuration(context));
	}

	@Override
	protected String previewParseToString(TripleDurationInfo dateValue) {
		return FuzzyDateParser.durationToFuzzyString(dateValue.getEstimate()) + " hours";
	}

	@Override
	protected String getParseEmptyText() {
		return "0";
	}
	
}
