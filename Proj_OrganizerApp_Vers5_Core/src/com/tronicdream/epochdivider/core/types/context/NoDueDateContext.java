package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for tasks that have no due dates. 
 * @author Ahmed El-Hajjar
 */
public class NoDueDateContext extends Context {
	public NoDueDateContext() {
		super();
		setName("No Due Date");
	}
	
	public boolean isSelectable() {return false;}
	public int getColor() {return ContextConstants.DUE_DATE_CONTEXT_COLOR;} 
}
