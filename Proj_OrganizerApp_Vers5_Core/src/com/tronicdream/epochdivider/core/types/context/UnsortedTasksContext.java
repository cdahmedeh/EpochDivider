package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for filtering tasks that are not assigned to no context.
 * 
 * @author Ahmed El-Hajjar
 */
public class UnsortedTasksContext extends Context {
	public UnsortedTasksContext() {
		super();
		setName("Unsorted");
	}
	
	public boolean isSelectable() {return true;}
	public int getColor() {return ContextConstants.META_CONTEXT_COLOR;} 
}
