package org.jdesktop.swingx;

import javax.swing.event.ChangeEvent;

/**
 * JXTreeTable with rendering hacks. 
 * 
 * When editing an element within the tree, sometimes, the text part would not 
 * expand correctly.
 * 
 * For example, if there was "Long Text" in the tree, and it was edited to 
 * "Even Longer Text", it would render as "Even Long...". This overrides 
 * the editingStopped and editingCancelled methods and forces re-rendering of the
 * tree.  
 * 
 * @author Ahmed El-Hajjar
 */
public class CTreeTable extends JXTreeTable {
	private static final long serialVersionUID = -3747870363565259953L;

	@Override
	public void editingStopped(ChangeEvent e) {
		super.editingStopped(e);
		updateHierarchicalRendererEditor();
	}

	@Override
	public void editingCanceled(ChangeEvent e) {
		// TODO Auto-generated method stub
		super.editingCanceled(e);
		updateHierarchicalRendererEditor();
	}
}
