package org.cdahmedeh.orgapp.types.context;

/**
 * Meta-context for tasks that are due tomorrow. 
 * @author Ahmed El-Hajjar
 */
public class DueTomorrowContext extends Context {
	public DueTomorrowContext() {super("Due Tomorrow");}
	public boolean isSelectable() {return false;}
}
