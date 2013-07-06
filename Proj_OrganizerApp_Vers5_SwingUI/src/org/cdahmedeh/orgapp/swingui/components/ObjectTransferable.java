package org.cdahmedeh.orgapp.swingui.components;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public abstract class ObjectTransferable<T> implements Transferable {
	private Class<T> clazz;
	private T object;
	private final DataFlavor[] SUPPORTED_FLAVOURS;
	
	public ObjectTransferable(Class<T> clazz, T task) {
		SUPPORTED_FLAVOURS = new DataFlavor[] {new DataFlavor(clazz, clazz.getSimpleName())};
		this.clazz = clazz;
		this.object = task;
	}
	
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for (DataFlavor dataFlavor: SUPPORTED_FLAVOURS) {
			if (dataFlavor.equals(flavor)) return true;	
		}
		return false;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return SUPPORTED_FLAVOURS;
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (object == null) {
			throw new IOException("Object is null in ObjectTransferable");
		}
		
		if (flavor.getHumanPresentableName().equals(clazz.getSimpleName())){
			return object;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}
}
