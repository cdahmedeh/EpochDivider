package org.cdahmedeh.orgapp.types.category;

public class AllCategories extends Category {
	public AllCategories() {super("All Categories");}
	@Override public boolean isVisible() {return false;}	
}
