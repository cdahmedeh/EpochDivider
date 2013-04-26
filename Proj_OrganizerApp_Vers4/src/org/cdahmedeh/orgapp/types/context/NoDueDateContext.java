package org.cdahmedeh.orgapp.types.context;

/**
 * Meta-context for tasks that have no due dates. 
 * @author Ahmed El-Hajjar
 */
public class NoDueDateContext extends Context {
	public NoDueDateContext() {super("No Due Date");}
	public boolean isSelectable() {return false;}
}
