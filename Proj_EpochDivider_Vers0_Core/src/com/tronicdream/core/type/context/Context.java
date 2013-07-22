package com.tronicdream.core.type.context;

import com.tronicdream.core.type.category.Category;

/**
 * A {@link Context} groups multiple Events or Tasks together. Contexts allow
 * users to organize their events or tasks into easy to find groups. Contexts
 * may represent a situation, location or even a project depending on the users
 * preference.
 * 
 * @author Ahmed El-Hajjar
 */
public class Context {
	
	/* - Primary Fields - */
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	
	/* - Relationships - */
	private Category category = null;
	public Category getCategory() {return category;}
	public void setCategory(Category category) {this.category = category;}
}
