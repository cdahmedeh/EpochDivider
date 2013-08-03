package com.tronicdream.epochdivider.swingui.eventlist;

import java.util.Comparator;

import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.event.Event;

public class EventListTableFormat implements AdvancedTableFormat<Event>, WritableTableFormat<Event> {

	@Override
	public Object getColumnValue(Event baseObject, int column) {
		switch(column){
		case EventListPanelConstants.COLUMN_EVENT_TITLE: 
			return baseObject.getTitle();
		case EventListPanelConstants.COLUMN_EVENT_CONTEXT: 
			return baseObject.getContext();				
		}
		
		return "";
	}
	
	@Override
	public Event setColumnValue(Event baseObject, Object editedValue, int column) {
		switch(column){
		case EventListPanelConstants.COLUMN_EVENT_TITLE:
			baseObject.setTitle((String) editedValue);
			break;
		case EventListPanelConstants.COLUMN_EVENT_CONTEXT:
			if (editedValue != null && editedValue instanceof Context){
				baseObject.setContext((Context)editedValue);
			}
			break;
		}
		
		return baseObject;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column){
		case EventListPanelConstants.COLUMN_EVENT_TITLE: 		return "Event";
		case EventListPanelConstants.COLUMN_EVENT_CONTEXT: 	return "Context";
		}
		return "";
	}
	
	@Override
	public Class<?> getColumnClass(int column) {
		switch(column){
		case EventListPanelConstants.COLUMN_EVENT_TITLE: 		return String.class;
		case EventListPanelConstants.COLUMN_EVENT_CONTEXT: 	return Context.class;
		}
		return null;
	}
	
	@Override
	public Comparator<?> getColumnComparator(int column) {
		switch(column){
		}
		return null;
	}
	
	@Override
	public boolean isEditable(Event baseObject, int column) {
		return true;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}
	
}
