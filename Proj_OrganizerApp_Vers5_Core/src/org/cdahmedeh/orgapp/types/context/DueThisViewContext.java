package org.cdahmedeh.orgapp.types.context;

/**
 * Meta-context for tasks that have a due date within the current View. 
 * @author Ahmed El-Hajjar
 */
public class DueThisViewContext extends Context {
	public DueThisViewContext() {super("Due this View");}
	public boolean isSelectable() {return false;}
}
