package org.cdahmedeh.orgapp.types.containers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Context;
import org.junit.Test;

public class DataContainerTest {

	@Test
	public void testContextsListBasically() {
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
	
	@Test
	public void testEmAddNewContext() {
		DataContainer dataContainer = new DataContainer();
		
		int oldSize = dataContainer.getContexts().size();
		dataContainer.emAddNewContext();
		int newSize = dataContainer.getContexts().size();
		
		//Contexts list should grow by one unit
		assertEquals(1, newSize - oldSize);
		
		//The last one should be the new context
		Context context = dataContainer.getContexts().get(dataContainer.getContexts().size()-1);
		assertEquals("", context.getName());
	}

}
