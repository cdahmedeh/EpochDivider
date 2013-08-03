package com.tronicdream.epochdivider.core.types.event;

import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.types.context.AllEventsContext;
import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.view.View;

/**
 * For matching event to certain criteria. 
 * 
 * @author Ahmed El-Hajjar
 */
public class EventMatcher {
	public static boolean matchByContext(Event item, Context context, View view) {
		if (context instanceof AllEventsContext) return true;
		if (item.getContext().equals(context)) return true;
		return false;
	}
	
	public static boolean matches(Event item, DataContainer dataContainer) {
		return matchByContext(item, dataContainer.getSelectedEventContext(), dataContainer.getView());
	}
}
