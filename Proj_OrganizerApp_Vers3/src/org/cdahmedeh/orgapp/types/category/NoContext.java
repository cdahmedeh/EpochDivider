package org.cdahmedeh.orgapp.types.category;

public class NoContext extends Context {
	public NoContext() {super("Unsorted");}
	@Override public boolean isVisible() {return false;}
}
