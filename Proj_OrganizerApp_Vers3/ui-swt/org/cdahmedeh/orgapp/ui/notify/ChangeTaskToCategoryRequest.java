package org.cdahmedeh.orgapp.ui.notify;

import org.cdahmedeh.orgapp.types.category.Context;

public class ChangeTaskToCategoryRequest {
	private int id = 0;
	public int getId() {
		return id;
	}
	
	private Context category = null;
	public Context getCategory() {
		return category;
	}
	
	public ChangeTaskToCategoryRequest(int id, Context category) {
		this.id = id;
		this.category = category;
	}

}
