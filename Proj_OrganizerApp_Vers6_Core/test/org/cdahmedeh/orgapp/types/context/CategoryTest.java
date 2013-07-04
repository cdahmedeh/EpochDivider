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
}
