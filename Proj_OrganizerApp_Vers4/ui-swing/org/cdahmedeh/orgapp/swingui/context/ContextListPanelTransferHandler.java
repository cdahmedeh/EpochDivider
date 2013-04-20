//package org.cdahmedeh.orgapp.swingui.context;
//
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.Transferable;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.io.IOException;
//
//import javax.swing.JComponent;
//import javax.swing.JTable;
//import javax.swing.TransferHandler;
//import javax.swing.tree.TreePath;
//
//import org.cdahmedeh.orgapp.swingui.notification.RefreshContextListRequest;
//import org.cdahmedeh.orgapp.types.context.Context;
//import org.cdahmedeh.orgapp.types.context.ContextCategory;
//import org.jdesktop.swingx.CTreeTable;
//
//import com.google.common.eventbus.EventBus;
//
///**
// * TransferHandler for allowing dragging and dropping between elements in the 
// * Context list tree.
// * 
// * Horrible, horrible, horrible code.
// * 
// */
//public class ContextListPanelTransferHandler extends TransferHandler {
//	private static final long serialVersionUID = -6832754781708822498L;
//	
//	private CTreeTable contextListTreeTable;
//
//	private EventBus eventBus;
//
//	public ContextListPanelTransferHandler(CTreeTable contextListTreeTable, EventBus eventBus) {
//		this.contextListTreeTable = contextListTreeTable;
//		this.eventBus = eventBus;
//	}
//
//	@Override
//	public boolean canImport(TransferSupport support) {
//		return true;
//	}
//
//	@Override
//	public boolean importData(TransferSupport support) {
//		//When dropping a context into another context, it will be put in the same category.
//		int row = ((JTable.DropLocation)support.getDropLocation()).getRow();
//		if (row - 1 < 0) return false;
//		
//		Context transferContextData = null;
//		ContextCategory transferContextCategoryData = null;
//		try {
//			transferContextData = (Context)support.getTransferable().getTransferData(new DataFlavor(Context.class, "Context"));
//			transferContextCategoryData = (ContextCategory)support.getTransferable().getTransferData(new DataFlavor(Context.class, "ContextCategory"));
//		} catch (UnsupportedFlavorException | IOException e) {
//			e.printStackTrace();
//		}
//		
//		ContextCategory categoryFromRow = getContextCategoryFromRow(row-1);
//		if (categoryFromRow != null && transferContextData != null && transferContextCategoryData != null){
//			transferContextCategoryData.getContexts().remove(transferContextData);
//			int indexOf = categoryFromRow.getContexts().indexOf(getContextFromRow(row));
//			if (indexOf == -1){
//				ContextCategory contextCategoryFromRow = getContextCategoryFromRow(row);
//				if (contextCategoryFromRow == null){
//					contextCategoryFromRow = getContextCategoryFromRow(row-1);
//				}
//				if (contextCategoryFromRow != null){
//					if (contextCategoryFromRow.equals(transferContextCategoryData)){
//						categoryFromRow.getContexts().add(0,transferContextData);
//					} else {
//						categoryFromRow.getContexts().add(transferContextData);
//					}
//				} else {
//					categoryFromRow.getContexts().add(0,transferContextData);
//				}
//					
//			} else {
//				categoryFromRow.getContexts().add(indexOf,transferContextData);
//			}
//			
//		}
//		
//		eventBus.post(new RefreshContextListRequest());
//		
//		return true;
//	}
//
//	@Override
//	public int getSourceActions(JComponent c) {
//		return MOVE;
//	}
//
//	@Override
//	protected Transferable createTransferable(JComponent c) {
//		Context contextFromRow = getContextFromRow(contextListTreeTable.getSelectedRow());
//		ContextCategory contextCategoryFromRow = getContextCategoryFromRow(contextListTreeTable.getSelectedRow());
//		if (contextFromRow != null){
//			return new ContextTransferable(contextFromRow, contextCategoryFromRow);
//		}
//		return null;
//	}
//	
//	private Context getContextFromRow(int row) {
//		TreePath pathForRow = contextListTreeTable.getPathForRow(row);
//		if (pathForRow == null) return null;
//		Object selectedObject = pathForRow.getLastPathComponent();
//		if (selectedObject instanceof Context){
//			return (Context)selectedObject;
//		} 
//		return null;
//	}
//	
//	private ContextCategory getContextCategoryFromRow(int row) {
//		TreePath pathForRow = contextListTreeTable.getPathForRow(row);
//		if (pathForRow == null) return null;
//		Object selectedObject = pathForRow.getLastPathComponent();
//		Object parentObject = pathForRow.getParentPath().getLastPathComponent();
//		if (selectedObject instanceof ContextCategory){
//			return (ContextCategory)selectedObject;
//		} else if (parentObject instanceof ContextCategory){
//			return (ContextCategory)parentObject;	
//		}
//		return null;
//	}
//}