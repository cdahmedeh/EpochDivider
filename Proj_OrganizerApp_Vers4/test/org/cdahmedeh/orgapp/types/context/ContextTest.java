package org.cdahmedeh.orgapp.types.context;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContextTest {
	@Test public void settingNameInConstructorShouldSetName(){
		assertEquals("Name Test", new Context("Name Test").getName());
	}

	@Test public void settingNameInConstructorAsNullShouldReallySetItToBlankString(){
		Context Context = new Context(null);
		assertEquals("", Context.getName());
	}

	@Test public void settingNameWithSetNameMethodShouldAlsoWork(){
		Context context = new Context("");
		context.setName("Name Test");
		assertEquals("Name Test", context.getName());
	}

	@Test public void settingNameWithSetNameMethodShouldRemoveTrailingWhiteSpace(){
		Context context = new Context("");
		context.setName(" \n  Name Test With Whitespace  ");
		assertEquals("Name Test With Whitespace", context.getName());
	}
	
	@Test public void settingNameAsNullShouldReallySetItToBlankString(){
		Context context = new Context("");
		context.setName(null);
		assertEquals("", context.getName());
	}
}
