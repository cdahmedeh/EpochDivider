package org.cdahmedeh.orgapp.schedule;

import org.eclipse.swt.graphics.RGB;

public class Category {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public RGB getColor() {
		return color;
	}
	public void setColor(RGB color) {
		this.color = color;
	}
	private RGB color;
	
	public Category(String name, RGB color) {
		this.name = name;
		this.color = color;
	}
}
