package org.cdahmedeh.orgapp.types.category;

public class Category {
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name != null ? name.trim() : "";}
}