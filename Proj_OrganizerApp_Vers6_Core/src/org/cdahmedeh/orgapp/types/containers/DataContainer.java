package org.cdahmedeh.orgapp.types.containers;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Context;

/**
 * {@link DataContainer} is an object with contains references to relevant
 * data for a single user.  
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	
	/* - Main data - */
	
	// List of categories (which contain the contexts) //
	private ArrayList<Context> contexts = new ArrayList<>();
	public ArrayList<Context> getContexts() {return contexts;}
	
	
	/* - Easy Modifiers - */
	
	/**
	 * Creates a new Context with a blank name and adds it to the contexts list.
	 */
	public void emAddNewContext() {
		contexts.add(new Context(null));
	}
}
