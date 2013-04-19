package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.ContextCategory;

/**
 * Contains method to generate some test data.
 * 
 * @author Ahmed El-Hajjar
 */
public class TestDataGenerator {
	public static DataContainer generateDataContainer(){
		DataContainer dataContainer = new DataContainer();
		dataContainer.setContextCategories(generateListOfContextCategories());
		return dataContainer;
	}
	
	private static ArrayList<ContextCategory> generateListOfContextCategories(){
		ArrayList<ContextCategory> contextList = new ArrayList<>();
		
		ContextCategory essentialsContextCategory = new ContextCategory("Essentials");
		contextList.add(essentialsContextCategory);
		
		Context faithContext = new Context("Faith");
		essentialsContextCategory.getContexts().add(faithContext);
		
		Context sleepContext = new Context("Sleep");
		essentialsContextCategory.getContexts().add(sleepContext);
		
		Context relaxingContext = new Context("Relaxing");
		essentialsContextCategory.getContexts().add(relaxingContext);
		
		Context exerciseContext = new Context("Exercise");
		essentialsContextCategory.getContexts().add(exerciseContext);
		
		ContextCategory universityContextCategory = new ContextCategory("University");
		contextList.add(universityContextCategory);
		
		Context studyContext = new Context("Study");
		universityContextCategory.getContexts().add(studyContext);
	
		Context coursesContext = new Context("Courses");
		universityContextCategory.getContexts().add(coursesContext);
		
		ContextCategory blankContextCategory = new ContextCategory("Blank");
		contextList.add(blankContextCategory);
		
		return contextList;
	}
}
