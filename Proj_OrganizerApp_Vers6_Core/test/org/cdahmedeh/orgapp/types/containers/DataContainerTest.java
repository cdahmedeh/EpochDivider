package org.cdahmedeh.orgapp.types.containers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Category;
import org.junit.Test;

public class DataContainerTest {

	@Test
	public void testCategoriesListBasically() {
		DataContainer dataContainer = new DataContainer();
		ArrayList<Category> categories = dataContainer.getCategories();
		
		//By default, the data container should have no categories.
		assertTrue(categories.isEmpty());
		
		//Add some categories 
		Category category0 = new Category("zero");
		Category category1 = new Category("one");
		
		categories.add(category0);
		categories.add(category1);
		
		//Check size and equality
		assertEquals(2, categories.size());
		assertEquals(category0, categories.get(0));
		assertEquals(category1, categories.get(1));
	}

}
