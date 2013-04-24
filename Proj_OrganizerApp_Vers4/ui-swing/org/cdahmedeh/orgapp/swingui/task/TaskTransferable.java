package org.cdahmedeh.orgapp.swingui.task;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.cdahmedeh.orgapp.types.task.Task;

public class TaskTransferable implements Transferable {
	private Task task;
	public TaskTransferable(Task task) {this.task = task;}
	
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.getHumanPresentableName().equals("Task")){
			return true;
		}
		return false;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] {new DataFlavor(Task.class, "Task")};
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (task == null) throw new IOException("Task is null in TaskTransferable");
		
		if (flavor.getHumanPresentableName().equals("Task")){
			return task;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}
}