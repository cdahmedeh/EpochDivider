package org.cdahmedeh.orgapp.types.context;

/**
 * Meta-context for tasks that are due today. 
 * @author Ahmed El-Hajjar
 */
public class DueTodayContext extends Context {
	public DueTodayContext() {super("Due Today");}
	public boolean isSelectable() {return false;}
	public int getColor() {return 150;} 
}
