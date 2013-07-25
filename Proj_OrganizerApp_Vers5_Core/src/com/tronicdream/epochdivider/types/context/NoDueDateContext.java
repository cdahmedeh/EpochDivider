package com.tronicdream.epochdivider.types.context;

/**
 * Meta-context for tasks that have no due dates. 
 * @author Ahmed El-Hajjar
 */
public class NoDueDateContext extends Context {
	public NoDueDateContext() {super("No Due Date");}
	public boolean isSelectable() {return false;}
	public int getColor() {return 150;} 
}
