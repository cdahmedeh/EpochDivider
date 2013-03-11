package org.cdahmedeh.orgapp.ui.notify;

import org.cdahmedeh.orgapp.types.category.Category;

public class CategoryChangedNotification {
	private Category category = null;
	public Category getCategory() {
		return category;
	}
	
	public CategoryChangedNotification(Category category) {
		this.category = category;
	}
}
