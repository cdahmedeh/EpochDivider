package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;

/**
 * Contains method to generate some test data.
 * 
 * @author Ahmed El-Hajjar
 */
public class TestDataGenerator {
	public static DataContainer generateDataContainer(){
		DataContainer dataContainer = new DataContainer();
		dataContainer.setRootContext(generateContextTree());
		return dataContainer;
	}
	
	private static Context generateContextTree(){
		Context contextList = new Context("Root");
		
		Context essentialsContext = new Context("Essentials");
		contextList.getSubContexts().add(essentialsContext);
		
		Context faithContext = new Context("Faith");
		essentialsContext.getSubContexts().add(faithContext);
		
		Context sleepContext = new Context("Sleep");
		essentialsContext.getSubContexts().add(sleepContext);
		
		Context relaxingContext = new Context("Relaxing");
		essentialsContext.getSubContexts().add(relaxingContext);
		
		Context exerciseContext = new Context("Exercise");
		essentialsContext.getSubContexts().add(exerciseContext);
		
		Context universityContext = new Context("University");
		contextList.getSubContexts().add(universityContext);
		
		Context studyContext = new Context("Study");
		universityContext.getSubContexts().add(studyContext);
	
		Context coursesContext = new Context("Courses");
		universityContext.getSubContexts().add(coursesContext);
		
		Context blankContext = new Context("Blank");
		contextList.getSubContexts().add(blankContext);
		
		return contextList;
	}
}
