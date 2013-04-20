//package org.cdahmedeh.orgapp.swingui.context;
//
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.Transferable;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.io.IOException;
//
//import org.cdahmedeh.orgapp.types.context.Context;
//import org.cdahmedeh.orgapp.types.context.ContextCategory;
//
//public class ContextTransferable implements Transferable {
//	private Context context;
//	private ContextCategory contextCategory;
//	
//	public ContextTransferable(Context context, ContextCategory contextCategory) {
//		this.context = context;
//		this.contextCategory = contextCategory;
//	}
//	
//	@Override
//	public boolean isDataFlavorSupported(DataFlavor flavor) {
//		if (flavor.getClass().equals(Context.class)) {
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public DataFlavor[] getTransferDataFlavors() {
//		return new DataFlavor[] {new DataFlavor(Context.class, "Context"), new DataFlavor(ContextCategory.class, "ContextCategory")};
//	}
//
//	@Override
//	public Object getTransferData(DataFlavor flavor)
//			throws UnsupportedFlavorException, IOException {
//		if (flavor.getHumanPresentableName().equals("Context")){
//			return context;
//		} else if (flavor.getHumanPresentableName().equals("ContextCategory")){
//			return contextCategory;
//		}
//		return null;
//	}
//
//}