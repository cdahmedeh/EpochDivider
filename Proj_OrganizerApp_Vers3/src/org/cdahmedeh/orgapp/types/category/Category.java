package org.cdahmedeh.orgapp.types.category;

public class Category {
	public Category(String name) {this.setName(name); id = idCounter++;}

	private static int idCounter = 0;
	private int id = -1;
	public int getId() {return id;}

	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name != null ? name.trim() : "";}
}