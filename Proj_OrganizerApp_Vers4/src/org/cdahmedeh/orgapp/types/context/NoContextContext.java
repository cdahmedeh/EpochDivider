package org.cdahmedeh.orgapp.types.context;

/**
 * Meta-context for filtering tasks that are not assigned to no context.
 * (Actually NoContext type)  
 * @author Ahmed El-Hajjar
 */
public class NoContextContext extends Context {
	public NoContextContext() {super("No Context");}
	public boolean isSelectable() {return true;}
}
