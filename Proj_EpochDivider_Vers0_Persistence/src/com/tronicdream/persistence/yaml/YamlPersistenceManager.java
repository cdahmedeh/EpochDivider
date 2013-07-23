package com.tronicdream.persistence.yaml;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import com.tronicdream.core.container.DataContainer;
import com.tronicdream.persistence.main.PersistenceManagerInterface;

public class YamlPersistenceManager implements PersistenceManagerInterface {

	@Override
	public boolean saveDataToFile(DataContainer dataContainer, File file) {
		// Convert DataContainer into Yaml
		String dump = new Yaml().dump(dataContainer);
		
		// Save the Yaml data into a flat file.
		try {
			FileUtils.writeStringToFile(file, dump);
		} catch (IOException e) {
			//TODO: Do something with exception
			return false;
		}
		return true;
	}

	@Override
	public DataContainer loadDataFromFile(File file) {
		Yaml yaml = new Yaml();
		try {
			String result = FileUtils.readFileToString(file);
			Object load = yaml.load(result);
			return (DataContainer) load;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
