package org.cdahmedeh.orgapp.types.category;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.Duration;

public class AllContexts extends Context {
	public AllContexts() {super("All Contexts");}
	@Override public boolean isSelectable() {return false;}
}
