package org.cdahmedeh.orgapp.swingui.components;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.cdahmedeh.orgapp.tools.FuzzyDateParser;
import org.joda.time.DateTime;

import com.jidesoft.popup.JidePopup;

/**
 * Editor for dates, still slightly buggy. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DateEntryCellEditor extends DefaultCellEditor{
	private static final long serialVersionUID = -5881533302874309679L;

	private String datePreviewText = "";
	private DateTime dateValue;
	
	public DateEntryCellEditor(final JTextField editorTextField) {
		super(editorTextField);

		//Setup delegate, remove old one, and put new one in with date parser.
        editorTextField.removeActionListener(delegate);
		
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 7646999407630619180L;

			public void setValue(Object value) {
                editorTextField.setText((value != null) ? (FuzzyDateParser.dateTimeToFuzzyString(((DateTime)value))) : "");
            }

            public Object getCellEditorValue() {
                return editorTextField.getText();
            }
        };

        editorTextField.addActionListener(delegate);

        //Setup popup preview display and update date value.
        final JidePopup popup = new JidePopup();
        popup.getContentPane().setLayout(new BorderLayout());
        final JTextField comp = new JTextField("");
        popup.add(comp);
        
        //When editor field is updated, try parsing the date, and show it in the preview
        //TODO: Reuse old popup
        editorTextField.addCaretListener(new CaretListener() {
        	@Override
        	public void caretUpdate(CaretEvent e) {
        		new Thread(new Runnable() {	public void run() {
					DateTime dateFromParser = FuzzyDateParser.fuzzyStringToDateTime(editorTextField.getText());
					if (dateFromParser != null) {
						dateValue = dateFromParser;
						datePreviewText = dateValue.toString();
	        		}
	
	        		comp.setText(datePreviewText);
	        		popup.setOwner(editorTextField);
	        		popup.showPopup();
				}}).start();
        	}
        });
	}
	
	@Override
	public Object getCellEditorValue() {
		return dateValue;
	}
}
