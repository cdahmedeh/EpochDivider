package org.cdahmedeh.orgapp.swingui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.jidesoft.hints.AbstractListIntelliHints;

/**
 * Editor for parsed types, still slightly buggy. At least now, it's reusable 
 * buggy code that will only need to fixed once. 
 * 
 * @author Ahmed El-Hajjar
 */
public abstract class AbstractParserEntryCellEditor<T> extends DefaultCellEditor{
	private static final long serialVersionUID = -5881533302874309679L;

	//Data
	private T objectValue;

	//Components
	private JTextField editorTextField;

	private IntelliHintsTextPreview intelliHintsTextPreview;

	public AbstractParserEntryCellEditor(final JTextField editorTextField) {
		super(editorTextField);
		
		this.editorTextField = editorTextField;
		
		setupDelegate();
        setupDatePreviewPopup();
	}

	private void setupDelegate() {
		//Setup delegate, remove old one, and put new one in with date parsing.
        editorTextField.removeActionListener(delegate);
		delegate = new DateParsedDeligateExtension(editorTextField);
        editorTextField.addActionListener(delegate);
	}
	
	private void setupDatePreviewPopup() {
        intelliHintsTextPreview = new IntelliHintsTextPreview(editorTextField);
        intelliHintsTextPreview.setShowHintsDelay(0);
	}

	
	private final class IntelliHintsTextPreview extends
			AbstractListIntelliHints {
		protected JLabel previewLabel;
		String previewText = "";

		private IntelliHintsTextPreview(JTextComponent textComponent) {
			super(textComponent);
			
			textComponent.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					showHints();
				}
			});
		}

		@Override
		public JComponent createHintsComponent() {
		    JPanel panel = (JPanel) super.createHintsComponent();
		    previewLabel = new JLabel();
		    panel.add(previewLabel, BorderLayout.BEFORE_FIRST_LINE);
		    return panel;
		}

		@Override
		public boolean updateHints(Object context) {
			if (context instanceof String){
				T valueFromParser = parse((String)context);
				if (valueFromParser != null) {
					objectValue = valueFromParser;
					previewText = preview(objectValue);
				} else if (context.equals("")) {
					objectValue = null;
					previewText = getParseEmptyText();
				}
		        setListData(new Object[]{});
				previewLabel.setText(previewText);
			}
			return true;
		}
	}

	@Override
	public Object getCellEditorValue() {
		return objectValue;
	}
	
	private final class DateParsedDeligateExtension extends EditorDelegate {
		private static final long serialVersionUID = 7646999407630619180L;
		private final JTextField editorTextField;

		private DateParsedDeligateExtension(JTextField editorTextField) {
			this.editorTextField = editorTextField;
		}

		public void setValue(Object value) {
		    editorTextField.setText((value != null && !value.equals("")) ? (reparse(value)) : "");
		}

		public Object getCellEditorValue() {
		    return editorTextField.getText();
		}
	}
	
	protected abstract String reparse(Object value);
	protected abstract T parse(String value);
	protected abstract String preview(T dateValue);
	protected abstract String getParseEmptyText();
}
