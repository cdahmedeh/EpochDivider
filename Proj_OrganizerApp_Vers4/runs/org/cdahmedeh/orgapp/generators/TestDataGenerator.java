package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Context;

/**
 * Contains method to generate some test data.
 * 
 * @author Ahmed El-Hajjar
 */
public class TestDataGenerator {
	public static ArrayList<Context> generateListOfContexts(){
		ArrayList<Context> contextList = new ArrayList<>();
		
		contextList.add(new Context("Faith"));
		contextList.add(new Context("Sleep"));
		contextList.add(new Context("Relaxing"));
		contextList.add(new Context("Exercise"));
		
		return contextList;
	}
}
