package org.cdahmedeh.orgapp.swingui.components;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.joda.time.DateTime;

import com.jidesoft.popup.JidePopup;

/**
 * Editor for dates, still slightly buggy. 
 * 
 * TODO: Cleanup code.
 * TODO: Make sure that dates don't get changed if the user does not enter new text.
 * 
 * @author Ahmed El-Hajjar
 */
public class DateEntryCellEditor extends DefaultCellEditor{
	private static final long serialVersionUID = -5881533302874309679L;

	private String datePreviewText = "";
	private DateTime dateValue;

	private JidePopup datePreviewPopup;
	
	public DateEntryCellEditor(final JTextField editorTextField) {
		super(editorTextField);

		//Setup delegate, remove old one, and put new one in with date parsing.
        editorTextField.removeActionListener(delegate);
		delegate = new DateParsedDeligateExtension(editorTextField);
        editorTextField.addActionListener(delegate);

        datePreviewPopup = new JidePopup();
        datePreviewPopup.getContentPane().setLayout(new BorderLayout());
        final JTextField popupTextField = new JTextField("");
        datePreviewPopup.add(popupTextField, BorderLayout.CENTER);
		datePreviewPopup.setOwner(editorTextField);
        
        //When editor field is updated, try parsing the date, and show it in the preview
        //TODO: Reuse old popup
        editorTextField.addCaretListener(new CaretListener() {
        	@Override
        	public void caretUpdate(CaretEvent e) {
        		//Do parsing in seperate thread.
        		new Thread(new Runnable() {	public void run() {
					DateTime dateFromParser = FuzzyDateParser.fuzzyStringToDateTime(editorTextField.getText());
					if (dateFromParser != null) {
						dateValue = dateFromParser;
						datePreviewText = dateValue.toString("EEEE, MMMM d, YYYY 'at' HH:mm");
	        		}
	        		popupTextField.setText(datePreviewText);
	        		datePreviewPopup.showPopup();
				}}).start();
        	}
        });
	}
	
	@Override
	public Object getCellEditorValue() {
		return dateValue;
	}
	
	@Override
	public boolean stopCellEditing() {
		datePreviewPopup.hidePopup();
		return super.stopCellEditing();
	}

	@Override
	public void cancelCellEditing() {
		datePreviewPopup.hidePopup();
		super.cancelCellEditing();
	}
	
	private final class DateParsedDeligateExtension extends EditorDelegate {
		private static final long serialVersionUID = 7646999407630619180L;
		private final JTextField editorTextField;

		private DateParsedDeligateExtension(JTextField editorTextField) {
			this.editorTextField = editorTextField;
		}

		public void setValue(Object value) {
		    editorTextField.setText((value != null) ? (FuzzyDateParser.dateTimeToFuzzyString(((DateTime)value))) : "");
		}

		public Object getCellEditorValue() {
		    return editorTextField.getText();
		}
	}
}
