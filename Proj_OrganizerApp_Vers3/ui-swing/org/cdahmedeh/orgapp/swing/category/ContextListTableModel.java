package org.cdahmedeh.orgapp.swing.category;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.types.category.Context;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

public class ContextListTableModel implements TableModel {
	private final BigContainer bigContainer;
	public ContextListTableModel(BigContainer bigContainer) {this.bigContainer = bigContainer;}

    @Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Context context = bigContainer.getContextContainer().getAllContexts().get(rowIndex);
		switch(columnIndex){
		case 0:	
			return context.getName();
		case 1:
			ProgressInfo progressInfo = new ProgressInfo();
			progressInfo.first = 					
					context.getDurationPassedSince(
							bigContainer.getCurrentView().getStartDate().toDateTime(LocalTime.MIDNIGHT), 
							bigContainer.getCurrentView().getEndDate().toDateTime(LocalTime.MIDNIGHT), 
							bigContainer.getTaskContainer()
						).getStandardHours();
			progressInfo.third =
						context.getGoal(bigContainer.getCurrentView()).getStandardHours();
			return progressInfo;
		default:
			return "";
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Context context = bigContainer.getContextContainer().getAllContexts().get(rowIndex);
		
		switch(columnIndex){
		case 0:
			context.setName((String) aValue);
			return;
		case 1:
			context.setGoal(bigContainer.getCurrentView(), new Duration((long)(DateTimeConstants.MILLIS_PER_HOUR*Double.parseDouble((String)aValue))));
			return;
		}
	}



	@Override
	public int getRowCount() {
		return bigContainer.getContextContainer().getAllContexts().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
			case 0: return "Context";
			case 1: return "Progress";
			default: return "";
		}
	}

	@Override public boolean isCellEditable(int rowIndex, int columnIndex) {return true;}
	
	@Override public int getColumnCount() {return 2;}
	@Override public Class<?> getColumnClass(int columnIndex) {
//		if (columnIndex == 1) return ProgressInfo.class;
		return String.class;}

	@Override public void addTableModelListener(TableModelListener arg0) {}
	@Override public void removeTableModelListener(TableModelListener l) {}
}
