package org.cdahmedeh.orgapp.types.context;

/**
 * A {@link Context} defines an object by which tasks and events can be 
 * categorized. 
 * 
 * @author Ahmed El-Hajjar
 */
public class Context {
	
	/* --- Constructs --- */
	
	public Context(String name) {
		this.setName(name);
	}
	
	
	/* --- Primary Data --- */

	// Id //
	private static int idCounter = 0;
	private int id = idCounter++;
	public int getId() {return id;}
	
	// Display name of this context //
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name == null ? "" : name;}
}
