package org.cdahmedeh.orgapp.types.context;

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
}
