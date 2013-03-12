package org.cdahmedeh.orgapp.types.category;

public class NoCategory extends Category {
	public NoCategory() {super("No Category");}
	@Override public boolean isVisible() {return false;}
}
