package org.cdahmedeh.orgapp.run;

import org.cdahmedeh.orgapp.generators.TestDataGenerator;
import org.cdahmedeh.orgapp.pers.PersistenceManager;
import org.cdahmedeh.orgapp.types.container.DataContainer;

public class Run001PersistenceManager {
	public static void main(String[] args) {
		//Generate some test data.
		DataContainer generatedDataContainer = TestDataGenerator.generateDataContainer();
		
		//Save it
		PersistenceManager pm = new PersistenceManager();
		pm.saveDataContainer(generatedDataContainer);
		
		//Load it again
		DataContainer loadedDataContainer = pm.loadDataContainer();
	}
}
