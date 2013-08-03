package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for filtering tasks that are not assigned to no context.
 * 
 * @author Ahmed El-Hajjar
 */
public class UnsortedEventsContext extends Context {
	public UnsortedEventsContext() {
		super();
		setName("Unsorted Events");
	}
	
	public boolean isSelectable() {return true;}
	public int getColor() {return ContextConstants.META_CONTEXT_COLOR;} 
}
