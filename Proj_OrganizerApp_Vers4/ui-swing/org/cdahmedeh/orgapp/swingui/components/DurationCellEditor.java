package org.cdahmedeh.orgapp.swingui.components;

import javax.swing.JTextField;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.cdahmedeh.orgapp.types.task.TaskProgressInfo;
import org.joda.time.Duration;

/**
 * Editor for dates. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DurationCellEditor extends AbstractParserEntryCellEditor<TaskProgressInfo>{
	private static final long serialVersionUID = 4137237283040982074L;

	public DurationCellEditor(JTextField editorTextField) {
		super(editorTextField);
	}

	@Override
	protected String parseTypeToString(Object value) {
		return FuzzyDateParser.durationToFuzzyString(((TaskProgressInfo)value).getEstimate());
	}

	@Override
	protected TaskProgressInfo parseStringToType(String context) {
		return new TaskProgressInfo(null, null, FuzzyDateParser.fuzzyStringToDuration(context));
	}

	@Override
	protected String previewParseToString(TaskProgressInfo dateValue) {
		return FuzzyDateParser.durationToFuzzyString(dateValue.getEstimate()) + " hours";
	}

	@Override
	protected String getParseEmptyText() {
		return "0";
	}
	
}
