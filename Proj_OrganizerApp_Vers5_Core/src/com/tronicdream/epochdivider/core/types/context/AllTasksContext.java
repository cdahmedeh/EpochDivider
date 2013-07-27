package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for disabling filtering and getting all tasks. 
 * @author Ahmed El-Hajjar
 */
public class AllTasksContext extends Context {
	public AllTasksContext() {super("All Tasks");}
	public boolean isSelectable() {return false;}
	public int getColor() {return ContextConstants.META_CONTEXT_COLOR;} 
}
