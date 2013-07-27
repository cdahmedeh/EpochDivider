package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for filtering tasks that are not assigned to no context.
 * 
 * @author Ahmed El-Hajjar
 */
public class UnsortedTaskContext extends Context {
	public UnsortedTaskContext() {
		super();
		setName("Unsorted Tasks");
	}
	
	public boolean isSelectable() {return true;}
	public int getColor() {return ContextConstants.META_CONTEXT_COLOR;} 
}
