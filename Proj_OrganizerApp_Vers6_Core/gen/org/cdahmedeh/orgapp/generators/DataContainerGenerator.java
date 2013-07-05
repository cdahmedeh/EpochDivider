package org.cdahmedeh.orgapp.generators;

import org.cdahmedeh.orgapp.types.containers.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;

/**
 * Used to generate DataContainer objects for test purposes.
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainerGenerator {
	public static DataContainer generateBasicDataContainer(){
		DataContainer dataContainer = new DataContainer();
		
		dataContainer.getContexts().add(new Context("Faith"));
		dataContainer.getContexts().add(new Context("Sleep"));
		dataContainer.getContexts().add(new Context("News and Breakfast"));
		
		dataContainer.getContexts().add(new Context("Gaming"));
		dataContainer.getContexts().add(new Context("Tinkering"));
		
		return dataContainer;
	}
}
