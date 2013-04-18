package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.ContextCategory;

/**
 * Contains method to generate some test data.
 * 
 * @author Ahmed El-Hajjar
 */
public class TestDataGenerator {
	public static ArrayList<Context> generateListOfContexts(){
		ArrayList<Context> contextList = new ArrayList<>();
		
		ContextCategory essentialsContextCategory = new ContextCategory("Essentials");
		
		Context faithContext = new Context("Faith");
		faithContext.setCategory(essentialsContextCategory);
		contextList.add(faithContext);
		
		Context sleepContext = new Context("Sleep");
		sleepContext.setCategory(essentialsContextCategory);
		contextList.add(sleepContext);
		
		Context relaxingContext = new Context("Relaxing");
		relaxingContext.setCategory(essentialsContextCategory);
		contextList.add(relaxingContext);
		
		Context exerciseContext = new Context("Exercise");
		exerciseContext.setCategory(essentialsContextCategory);
		contextList.add(exerciseContext);
		
		ContextCategory universityContextCategory = new ContextCategory("University");
		
		Context studyContext = new Context("Study");
		studyContext.setCategory(universityContextCategory);
		contextList.add(studyContext);
		
		Context coursesContext = new Context("Courses");
		coursesContext.setCategory(universityContextCategory);
		contextList.add(coursesContext);
		
		return contextList;
	}
}
