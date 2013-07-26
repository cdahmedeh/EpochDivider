package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for filtering tasks that are not assigned to no context.
 * (Actually NoContext type)  
 * @author Ahmed El-Hajjar
 */
public class NoContextContext extends Context {
	public NoContextContext() {super("Unsorted Tasks");}
	public boolean isSelectable() {return true;}
	public int getColor() {return 0;} 
}
