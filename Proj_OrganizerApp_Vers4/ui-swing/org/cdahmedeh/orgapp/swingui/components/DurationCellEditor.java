package org.cdahmedeh.orgapp.swingui.components;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.cdahmedeh.orgapp.parsers.FuzzyDurationParser;
import org.cdahmedeh.orgapp.types.time.TripleDurationInfo;

/**
 * Editor for duration. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DurationCellEditor extends AbstractParserEntryCellEditor<TripleDurationInfo>{
	private static final long serialVersionUID = 4137237283040982074L;

	public DurationCellEditor(JTextField editorTextField) {
		super(editorTextField);
		editorTextField.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	@Override
	protected String parseTypeToString(Object value) {
		return FuzzyDurationParser.durationToFuzzyString(((TripleDurationInfo)value).getEstimate());
	}

	@Override
	protected TripleDurationInfo parseStringToType(String context) {
		return new TripleDurationInfo(null, null, FuzzyDurationParser.fuzzyStringToDuration(context));
	}

	@Override
	protected String previewParseToString(TripleDurationInfo dateValue) {
		return FuzzyDurationParser.durationToFuzzyString(dateValue.getEstimate()) + " hours";
	}

	@Override
	protected String getParseEmptyText() {
		return "0.0";
	}
	
}
