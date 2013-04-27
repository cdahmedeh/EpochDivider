package org.cdahmedeh.orgapp.swingui.context;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
import org.cdahmedeh.orgapp.types.context.Context;

import com.google.common.eventbus.EventBus;

import ca.odell.glazedlists.swing.DefaultEventSelectionModel;

/**
 * TransferHandler for allowing dragging and dropping between elements in the
 * Context list tree.
 * 
 * TODO: Code needs much cleanup. Now I understand why DnD is so hard to 
 * implement. (You have to interact with so much with so little data).
 * 
 */
public class ContextListPanelTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 247239613980407558L;
	
	private ArrayList<Context> contexts;

	private EventBus eventBus;

	public ContextListPanelTransferHandler(ArrayList<Context> contexts, EventBus eventBus) {
		this.contexts = contexts;
		this.eventBus = eventBus;
	}

	@Override
	public boolean canImport(TransferSupport support) {
		//Currently, only contexts can be dropped for reordering.
		return support.getTransferable().isDataFlavorSupported(new DataFlavor(Context.class, "Context"));
	}

	@Override
	public boolean importData(TransferSupport support) {
		//Get the context that was transfered.
		Context context = null;
		try {
			context = (Context) support.getTransferable().getTransferData(new DataFlavor(Context.class, "Context"));
		} catch (UnsupportedFlavorException | IOException e) {
			return false;
		}
		
		//Put the context in that location.
		int row = ((JTable.DropLocation)support.getDropLocation()).getRow();
		int indexOf = contexts.indexOf(context);
		contexts.remove(context);
		contexts.add(indexOf >= row ? row : row -1 , context);
		
		//Update table
		//TODO: Consider using notifications.
		eventBus.post(new RefreshContextListRequest());
		((JTable)support.getComponent()).getSelectionModel().setSelectionInterval(indexOf >= row ? row : row -1, indexOf >= row ? row : row -1);
		
		
		return true;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		Context context = null;
		if (c instanceof JTable && ((JTable) c).getSelectionModel() instanceof DefaultEventSelectionModel<?>) {
			context = (Context) ((DefaultEventSelectionModel<?>) ((JTable) c).getSelectionModel()).getSelected().get(0);
		}
		if (context == null) return null;
		return new ContextTransferable(context);
	}
}