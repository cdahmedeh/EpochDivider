package org.cdahmedeh.orgapp.types.category;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.category.Category;

public class CategoryContainer {
	public ArrayList<Category> categories = new ArrayList<>();
	public void addCategory(Category category) {categories.add(category);}
	public ArrayList<Category> getAllCategories() {return categories;}
	
	public Category getCategoryFromId(int id){
		for (Category category: categories) if (category.getId() == id) return category;
		return null;
	}
	
	public Category getCategoryFromName(String name){
		for (Category category: categories) if (category.getName().equals(name)) return category;
		return null;
	}
}
