package org.cdahmedeh.orgapp.ui.notify;

import org.cdahmedeh.orgapp.types.category.Category;

public class ChangeTaskToCategoryRequest {
	private int id = 0;
	public int getId() {
		return id;
	}
	
	private Category category = null;
	public Category getCategory() {
		return category;
	}
	
	public ChangeTaskToCategoryRequest(int id, Category category) {
		this.id = id;
		this.category = category;
	}

}
