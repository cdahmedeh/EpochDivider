package org.cdahmedeh.orgapp.types.category;

public class DueTomorrowContext extends Context {
	public DueTomorrowContext() {
		super("due tomorrow");
	}

	@Override public boolean isSelectable() {return false;}
}
