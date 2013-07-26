package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for disabling filtering and getting all tasks. 
 * @author Ahmed El-Hajjar
 */
public class AllContextsContext extends Context {
	public AllContextsContext() {super("All Tasks");}
	public boolean isSelectable() {return false;}
	public int getColor() {return 0;} 
}
