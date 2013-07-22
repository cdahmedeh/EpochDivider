package com.tronicdream.persistence.main;

import java.io.File;

import com.tronicdream.core.container.DataContainer;

/**
 * Interface for saving and loading DataContainer with files.
 * 
 * @author Ahmed El-Hajjar
 */
public interface PersistenceManagerInterface {
	
	/**
	 * Save the DataContainer to the provided file.
	 * 
	 * @return true if file is saved successfully, and false otherwise.
	 */
	public boolean saveDataToFile(DataContainer dataContainer, File file);
	
	/**
	 * Return the DataContainer represented by this file. 
	 * 
	 * @return DataContainer instance if successful, and null otherwise.
	 */
	public DataContainer loadDataFromFile(File file);
}
