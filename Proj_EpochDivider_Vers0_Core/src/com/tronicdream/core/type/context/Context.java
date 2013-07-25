package com.tronicdream.core.type.context;

/**
 * A {@link Context} groups multiple Events or Tasks together.
 * 
 * @author Ahmed El-Hajjar
 */
public class Context {
	
	/* - Primary Fields - */
	
	private int id = -1;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
}
