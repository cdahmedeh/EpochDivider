package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for tasks that are due today. 
 * @author Ahmed El-Hajjar
 */
public class DueTodayContext extends Context {
	public DueTodayContext() {
		super();
		setName("Due Today");
	}
	
	public boolean isSelectable() {return false;}
	public int getColor() {return ContextConstants.DUE_DATE_CONTEXT_COLOR;} 
}
