package org.cdahmedeh.orgapp.types.context;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class CategoryTest {
	@Test
	public void testCategoryIdsShouldIncrementInOrder() throws Exception {
		//Reset id counter back to 0 using Java Reflection
		Field idCounterField = Category.class.getDeclaredField("idCounter");
		idCounterField.setAccessible(true);
		idCounterField.set(null, 0);
		
		assertEquals(0, new Category("").getId());
		assertEquals(1, new Category("").getId());
		assertEquals(2, new Category("").getId());
		assertEquals(3, new Category("").getId());
		assertEquals(4, new Category("").getId());
	}
	
	@Test
	public void testCreatingCategorysWithNameShouldSetName() {
		assertEquals("Category", new Category("Category").getName());
		assertEquals("Test Category", new Category("Test Category").getName());
		assertEquals("SomeCategory", new Category("SomeCategory").getName());
		assertEquals("   SomeCategory", new Category("   SomeCategory").getName());
		assertEquals("   SomeCon  te   xt   ", new Category("   SomeCon  te   xt   ").getName());
		assertEquals("", new Category("").getName());
		assertEquals(" ", new Category(" ").getName());
		assertEquals("  ", new Category("  ").getName());
	}

	@Test
	public void testCreatingCategoryWithNullNameShouldSetNameToBlankString(){
		assertEquals("", new Category(null).getName());
	}
	
	@Test
	public void testSetAndGetCategoryName() {
		Category testCategory = new Category("");
		
		testCategory.setName("Category");
		assertEquals("Category", testCategory.getName());
		
		testCategory.setName("Test Category");
		assertEquals("Test Category", testCategory.getName());
		
		testCategory.setName("SomeCategory");
		assertEquals("SomeCategory", testCategory.getName());
		
		testCategory.setName("   SomeCategory");
		assertEquals("   SomeCategory", testCategory.getName());
		
		testCategory.setName("   SomeCon  te   xt   ");
		assertEquals("   SomeCon  te   xt   ", testCategory.getName());
		
		testCategory.setName("");
		assertEquals("", testCategory.getName());
		
		testCategory.setName(" ");
		assertEquals(" ", testCategory.getName());
		
		testCategory.setName("  ");
		assertEquals("  ", testCategory.getName());
	}

	@Test
	public void testSettingCategoryWithNullNameShouldSetNameToBlankString(){
		Category testCategory = new Category("Category");
		
		testCategory.setName(null);
		assertEquals("", testCategory.getName());
	}
	
	@Test
	public void testNewCategoryShouldHaveEmptyContextsList(){
		Category category = new Category("");

		assertEquals(0, category.getContextsAmount());
	}
	
	@Test
	public void testAddingContextsToCategory(){
		Category category = new Category("");
		
		Context context1 = new Context("one");
		Context context2 = new Context("two");
		Context context3 = new Context("three");
		
		category.addContext(context1);
		category.addContext(context2);
		category.addContext(context3);
		
		//Size should be three
		assertEquals(3, category.getContextsAmount());
		
		//Contexts should be the right ones and in order
		assertEquals(context1, category.getContextAtIndex(0));
		assertEquals(context2, category.getContextAtIndex(1));
		assertEquals(context3, category.getContextAtIndex(2));
	}
	
	@Test
	public void testGetIndexOfContext(){
		Category category = new Category("");
		
		Context context1 = new Context("one");
		Context context2 = new Context("two");
		Context context3 = new Context("three");
		
		category.addContext(context1);
		category.addContext(context2);
		category.addContext(context3);
		
		//Indexes should be in order
		assertEquals(0, category.getIndexOfContext(context1));
		assertEquals(1, category.getIndexOfContext(context2));
		assertEquals(2, category.getIndexOfContext(context3));
		
		//For a context not within the category, we should get -1
		assertEquals(-1, category.getIndexOfContext(new Context("blank")));
	}
}
