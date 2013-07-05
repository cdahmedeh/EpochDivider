package org.cdahmedeh.orgapp.generators;

import org.cdahmedeh.orgapp.types.containers.DataContainer;
import org.cdahmedeh.orgapp.types.context.Category;
import org.cdahmedeh.orgapp.types.context.Context;

/**
 * Used to generate DataContainer objects for test purposes.
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainerGenerator {
	public static DataContainer generateBasicDataContainer(){
		DataContainer dataContainer = new DataContainer();
		
		Category essentialsCategory = new Category("Essentials");
		essentialsCategory.addContext(new Context("Faith"));
		essentialsCategory.addContext(new Context("Sleep"));
		essentialsCategory.addContext(new Context("News and Breakfast"));
		
		Category leisureCategory = new Category("Leisure");
		leisureCategory.addContext(new Context("Gaming"));
		leisureCategory.addContext(new Context("Tinkering"));
		
		Category projectsCategory = new Category("Projects");
		
		dataContainer.getCategories().add(essentialsCategory);
		dataContainer.getCategories().add(leisureCategory);
		dataContainer.getCategories().add(projectsCategory);
		
		return dataContainer;
	}
}
