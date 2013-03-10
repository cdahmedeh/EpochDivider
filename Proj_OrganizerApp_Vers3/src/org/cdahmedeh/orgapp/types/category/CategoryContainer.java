package org.cdahmedeh.orgapp.types.category;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.category.Category;

public class CategoryContainer {
	public ArrayList<Category> categories = new ArrayList<>();
	public void addCategory(Category category) {categories.add(category);}
	public ArrayList<Category> getAllCategorys() {return categories;} //TODO: Should we clone?
}
