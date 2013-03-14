package org.cdahmedeh.orgapp.types.category;

import java.util.ArrayList;

public class CategoryContainer {
	public ArrayList<Context> categories = new ArrayList<>();
	public void addCategory(Context category) {categories.add(category);}
	public ArrayList<Context> getAllCategories() {return categories;}
	
	public ArrayList<Context> getAllVisibleCategories(){
		ArrayList<Context> cat = new ArrayList<>();
		for (Context category: categories) if (category.isVisible()) cat.add(category);
		return cat;
	}
	
	public Context getCategoryFromId(int id){
		for (Context category: categories) if (category.getId() == id) return category;
		return null;
	}
	
	public Context getCategoryFromName(String name){
		for (Context category: categories) if (category.getName().equals(name)) return category;
		return null;
	}
}
