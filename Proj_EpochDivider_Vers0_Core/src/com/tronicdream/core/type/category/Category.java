package com.tronicdream.core.type.category;

/**
 * A {@link Category} groups multiple Contexts together into a single package.
 *  
 * @author Ahmed El-Hajjar
 */
public class Category {

	/* - Primary Fields - */
	
	private int id = -1;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
}
