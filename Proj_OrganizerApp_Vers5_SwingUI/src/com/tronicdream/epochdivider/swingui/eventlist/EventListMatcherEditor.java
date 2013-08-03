package com.tronicdream.epochdivider.swingui.eventlist;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;

import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.types.event.EventMatcher;

/**
 * GlazedLists AbstractMatcherEditor implementation for filtering events on the
 * event list table.  
 * 
 * @author Ahmed El-Hajjar
 */
public class EventListMatcherEditor extends AbstractMatcherEditor<com.tronicdream.epochdivider.core.types.event.Event> {
	private DataContainer dataContainer;
	private Matcher<com.tronicdream.epochdivider.core.types.event.Event> matcher;

	public EventListMatcherEditor(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		this.matcher = new EventListMatcher();
	}

	public void matcherChangedNotify() {
		this.fireChanged(matcher);
	}

	private final class EventListMatcher implements Matcher<com.tronicdream.epochdivider.core.types.event.Event> {
		@Override
		public boolean matches(com.tronicdream.epochdivider.core.types.event.Event item) {
			return EventMatcher.matches(item, dataContainer);
		}
	}
}
