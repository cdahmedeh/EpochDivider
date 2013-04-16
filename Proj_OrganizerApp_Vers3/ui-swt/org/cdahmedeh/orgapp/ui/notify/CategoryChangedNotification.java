package org.cdahmedeh.orgapp.ui.notify;

import org.cdahmedeh.orgapp.types.category.Context;

public class CategoryChangedNotification {
	private Context category = null;
	public Context getCategory() {
		return category;
	}
	
	public CategoryChangedNotification(Context category) {
		this.category = category;
	}
}
