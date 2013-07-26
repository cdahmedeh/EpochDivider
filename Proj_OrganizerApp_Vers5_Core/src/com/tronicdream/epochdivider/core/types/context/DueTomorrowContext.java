package com.tronicdream.epochdivider.core.types.context;

/**
 * Meta-context for tasks that are due tomorrow. 
 * @author Ahmed El-Hajjar
 */
public class DueTomorrowContext extends Context {
	public DueTomorrowContext() {super("Due Tomorrow");}
	public boolean isSelectable() {return false;}
	public int getColor() {return 150;}
}
