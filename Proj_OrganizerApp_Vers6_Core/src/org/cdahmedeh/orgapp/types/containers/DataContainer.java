package org.cdahmedeh.orgapp.types.containers;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Category;

/**
 * {@link DataContainer} is an object with contains references to relevant
 * data for a single user.  
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	
	/* - Main data - */
	
	// List of categories (which contain the contexts) //
	private ArrayList<Category> categories = new ArrayList<>();
	public ArrayList<Category> getCategories() {return categories;}
}
