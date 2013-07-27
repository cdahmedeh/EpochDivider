package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for tasks that have a due date within the current View. 
 * @author Ahmed El-Hajjar
 */
public class DueThisViewContext extends Context {
	public DueThisViewContext() {
		super();
		setName("Due this View");
	}
	
	public boolean isSelectable() {return false;}
	public int getColor() {return ContextConstants.DUE_DATE_CONTEXT_COLOR;} 
}
