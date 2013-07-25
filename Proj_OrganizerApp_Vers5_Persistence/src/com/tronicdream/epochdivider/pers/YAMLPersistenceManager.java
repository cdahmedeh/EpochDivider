package com.tronicdream.epochdivider.pers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import com.tronicdream.epochdivider.types.container.DataContainer;
import org.yaml.snakeyaml.Yaml;

public class YAMLPersistenceManager implements PersistanceManagerInterface {
	@Override
	public DataContainer loadDataContainer(File file) {
		try {
			return (DataContainer) new Yaml().load(FileUtils.readFileToString(file));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public void saveDataContainer(File file, DataContainer dataContainer) {
		try {
			FileUtils.writeStringToFile(file, new Yaml().dump(dataContainer));
		} catch (IOException e) {
		}
	}
}
