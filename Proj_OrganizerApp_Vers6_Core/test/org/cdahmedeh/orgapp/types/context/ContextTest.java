package org.cdahmedeh.orgapp.types.context;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class ContextTest {
	@Test
	public void testContextIdsShouldIncrementInOrder() throws Exception {
		//Reset id counter back to 0 using Java Reflection
		Field idCounterField = Context.class.getDeclaredField("idCounter");
		idCounterField.setAccessible(true);
		idCounterField.set(null, 0);
		
		assertEquals(0, new Context("").getId());
		assertEquals(1, new Context("").getId());
		assertEquals(2, new Context("").getId());
		assertEquals(3, new Context("").getId());
		assertEquals(4, new Context("").getId());
	}
	
	@Test
	public void testCreatingContextsWithNameShouldSetName() {
		assertEquals("Context", new Context("Context").getName());
		assertEquals("Test Context", new Context("Test Context").getName());
		assertEquals("SomeContext", new Context("SomeContext").getName());
		assertEquals("   SomeContext", new Context("   SomeContext").getName());
		assertEquals("   SomeCon  te   xt   ", new Context("   SomeCon  te   xt   ").getName());
		assertEquals("", new Context("").getName());
		assertEquals(" ", new Context(" ").getName());
		assertEquals("  ", new Context("  ").getName());
	}

	@Test
	public void testCreatingContextWithNullNameShouldSetNameToBlankString(){
		assertEquals("", new Context(null).getName());
	}
	
	@Test
	public void testSetAndGetContextName() {
		Context testContext = new Context("");
		
		testContext.setName("Context");
		assertEquals("Context", testContext.getName());
		
		testContext.setName("Test Context");
		assertEquals("Test Context", testContext.getName());
		
		testContext.setName("SomeContext");
		assertEquals("SomeContext", testContext.getName());
		
		testContext.setName("   SomeContext");
		assertEquals("   SomeContext", testContext.getName());
		
		testContext.setName("   SomeCon  te   xt   ");
		assertEquals("   SomeCon  te   xt   ", testContext.getName());
		
		testContext.setName("");
		assertEquals("", testContext.getName());
		
		testContext.setName(" ");
		assertEquals(" ", testContext.getName());
		
		testContext.setName("  ");
		assertEquals("  ", testContext.getName());
	}

	@Test
	public void testSettingContextWithNullNameShouldSetNameToBlankString(){
		Context testContext = new Context("Context");
		
		testContext.setName(null);
		assertEquals("", testContext.getName());
	}
}
