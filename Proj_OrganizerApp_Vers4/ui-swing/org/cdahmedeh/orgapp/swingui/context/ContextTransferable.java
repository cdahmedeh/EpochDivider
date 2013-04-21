package org.cdahmedeh.orgapp.swingui.context;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.cdahmedeh.orgapp.types.context.Context;

public class ContextTransferable implements Transferable {
	private Context context;
	
	public ContextTransferable(Context context) {
		this.context = context;
	}
	
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.getHumanPresentableName().equals("Context")){
			return true;
		}
		return false;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] {new DataFlavor(Context.class, "Context")};
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.getHumanPresentableName().equals("Context")){
			return context;
		}
		return null;
	}
}