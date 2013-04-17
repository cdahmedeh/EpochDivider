package org.cdahmedeh.orgapp.types.category;

public class DueTodayContext extends Context {
	public DueTodayContext() {
		super("due today");
	}

	@Override public boolean isSelectable() {return false;}
}
