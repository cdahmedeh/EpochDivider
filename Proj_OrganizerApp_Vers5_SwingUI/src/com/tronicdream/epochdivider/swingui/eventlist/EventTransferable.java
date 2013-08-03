package com.tronicdream.epochdivider.swingui.eventlist;

import com.tronicdream.epochdivider.core.types.event.Event;
import com.tronicdream.epochdivider.swingui.components.ObjectTransferable;

public class EventTransferable extends ObjectTransferable<Event> {
	public EventTransferable(Event event) {
		super(Event.class, event);
	}
}