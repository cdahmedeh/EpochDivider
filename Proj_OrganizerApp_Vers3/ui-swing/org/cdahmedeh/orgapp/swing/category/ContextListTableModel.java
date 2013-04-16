package org.cdahmedeh.orgapp.swing.category;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.AllContexts;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.category.NoContext;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

public class ContextListTableModel implements TableModel {
	private final BigContainer bigContainer;
	public ContextListTableModel(BigContainer bigContainer) {this.bigContainer = bigContainer;}

    @Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Context context = bigContainer.getContextContainer().getAllContexts().get(rowIndex);
		View currentView = bigContainer.getCurrentView();
		
		switch(columnIndex){
		case ContextListColumns.NAME:
			return context.getName();
		case ContextListColumns.PROGRESS:
			ProgressInfo progressInfo = new ProgressInfo();
			progressInfo.color = context.getColor();
			progressInfo.first = context.getDurationPassedSince(
									currentView.getStartDate().toDateTime(LocalTime.MIDNIGHT), 
									currentView.getEndDate().toDateTime(LocalTime.MIDNIGHT), 
									bigContainer.getTaskContainer()
								).getStandardHours();
			if (context instanceof AllContexts)
				progressInfo.third = ((AllContexts) context).getGoal(currentView, bigContainer.getContextContainer()).getStandardHours();
			else if (context instanceof NoContext)
				progressInfo.third = ((NoContext) context).getGoal(currentView, bigContainer.getContextContainer()).getStandardHours();
			else
				progressInfo.third = context.getGoal(currentView).getStandardHours();
			return progressInfo;
		}
		return "";
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Context context = bigContainer.getContextContainer().getAllContexts().get(rowIndex);
		switch(columnIndex){
		case ContextListColumns.NAME:
			context.setName((String) aValue);
			return;
		case ContextListColumns.PROGRESS:
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

	@Override public int getColumnCount() {return 2;}
	@Override public boolean isCellEditable(int rowIndex, int columnIndex) {return true;}
	@Override public Class<?> getColumnClass(int columnIndex) {return String.class;}
	@Override public void addTableModelListener(TableModelListener arg0) {}
	@Override public void removeTableModelListener(TableModelListener l) {}
}
