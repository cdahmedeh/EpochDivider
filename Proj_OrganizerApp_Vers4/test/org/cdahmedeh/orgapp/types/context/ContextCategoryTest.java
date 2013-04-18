package org.cdahmedeh.orgapp.types.context;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContextCategoryTest {
	@Test public void settingNameInConstructorShouldSetName(){
		assertEquals("Name Test", new ContextCategory("Name Test").getName());
	}

	@Test public void settingNameInConstructorAsNullShouldReallySetItToBlankString(){
		ContextCategory ContextCategory = new ContextCategory(null);
		assertEquals("", ContextCategory.getName());
	}

	@Test public void settingNameWithSetNameMethodShouldAlsoWork(){
		ContextCategory contextCategory = new ContextCategory("");
		contextCategory.setName("Name Test");
		assertEquals("Name Test", contextCategory.getName());
	}

	@Test public void settingNameWithSetNameMethodShouldRemoveTrailingWhiteSpace(){
		ContextCategory contextCategory = new ContextCategory("");
		contextCategory.setName(" \n  Name Test With Whitespace  ");
		assertEquals("Name Test With Whitespace", contextCategory.getName());
	}
	
	@Test public void settingNameAsNullShouldReallySetItToBlankString(){
		ContextCategory contextCategory = new ContextCategory("");
		contextCategory.setName(null);
		assertEquals("", contextCategory.getName());
	}
}
