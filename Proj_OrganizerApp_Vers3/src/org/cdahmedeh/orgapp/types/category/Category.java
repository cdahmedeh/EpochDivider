package org.cdahmedeh.orgapp.types.category;

import org.eclipse.swt.graphics.RGB;

public class Category {
	public Category(String name) {this.setName(name); id = idCounter++;}

	private static int idCounter = 0;
	private int id = -1;
	public int getId() {return id;}

	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name != null ? name.trim() : "";}
	
	public RGB getColor(){
		return new RGB((float)id*20%255, 0.10f, 1f);
	}
}