package org.cdahmedeh.orgapp.types.context;

/**
 * Meta-context for disabling filtering and getting all tasks. 
 * @author Ahmed El-Hajjar
 */
public class AllContextsContext extends Context {
	public AllContextsContext() {super("All Contexts");}
	public boolean isSelectable() {return false;}
}
