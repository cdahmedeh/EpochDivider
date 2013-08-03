package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for disabling filtering and getting all tasks. 
 * @author Ahmed El-Hajjar
 */
public class AllEventsContext extends Context {
	public AllEventsContext() {
		super();
		setName("All Events");
	}
	
	public boolean isSelectable() {return false;}
	public int getColor() {return ContextConstants.META_CONTEXT_COLOR;} 
}
