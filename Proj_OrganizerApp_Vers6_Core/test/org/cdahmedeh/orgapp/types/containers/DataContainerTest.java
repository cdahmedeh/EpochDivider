package org.cdahmedeh.orgapp.types.containers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Context;
import org.junit.Test;

public class DataContainerTest {

	@Test
	public void testCategoriesListBasically() {
		DataContainer dataContainer = new DataContainer();
		ArrayList<Context> contexts = dataContainer.getContexts();
		
		//By default, the data container should have no contexts.
		assertTrue(contexts.isEmpty());
		
		//Add some contexts 
		Context context0 = new Context("zero");
		Context context1 = new Context("one");
		
		contexts.add(context0);
		contexts.add(context1);
		
		//Check size and equality
		assertEquals(2, contexts.size());
		assertEquals(context0, contexts.get(0));
		assertEquals(context1, contexts.get(1));
	}

}
