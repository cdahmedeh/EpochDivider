package org.cdahmedeh.orgapp.swingui.context;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * TransferHandler for allowing dragging and dropping between elements in the 
 * Context list tree.
 * 
 * Horrible, horrible, horrible code.
 * 
 */
public class ContextListPanelTransferHandler extends TransferHandler {
	@Override
	public boolean canImport(TransferSupport support) {
		return true;
	}

	@Override
	public boolean importData(TransferSupport support) {
		return true;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		return new StringSelection("");
	}
}