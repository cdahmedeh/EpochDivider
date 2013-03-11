package org.cdahmedeh.orgapp.types.category;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.category.Category;
import org.cdahmedeh.orgapp.types.task.Task;

public class CategoryContainer {
	public ArrayList<Category> categories = new ArrayList<>();
	public void addCategory(Category category) {categories.add(category);}
	public ArrayList<Category> getAllCategorys() {return categories;} //TODO: Should we clone?
	
	public Category getCategoryFromId(int id){
		for (Category category: categories) {
			if (category.getId() == id){
				return category;
			}
		}
		return null;
	}
}
