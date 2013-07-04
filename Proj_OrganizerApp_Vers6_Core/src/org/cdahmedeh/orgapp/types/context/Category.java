package org.cdahmedeh.orgapp.types.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A {@link Category} is a container for multiple {@link Context} objects. From 
 * a user perspective, it allows them to categorize contexts into categories.
 * 
 * @author Ahmed El-Hajjar
 */
public class Category {
	/* --- Constructs --- */
	
	public Category(String name) {
		this.setName(name);
	}
	
	
	/* --- Primary Data --- */

	// Id //
	private static int idCounter = 0;
	private int id = idCounter++;
	public int getId() {return id;}
	
	// Display name of this category //
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name == null ? "" : name;}
	
	
	/* --- Contained Data --- */
	
	// Contexts within this Category //
	private List<Context> contexts = new ArrayList<>();
	public void addContext(Context context) {this.contexts.add(context);}
	public Context getContextAtIndex(int index) {return this.contexts.get(index);}
	public int getContextsAmount() {return this.contexts.size();}
}
