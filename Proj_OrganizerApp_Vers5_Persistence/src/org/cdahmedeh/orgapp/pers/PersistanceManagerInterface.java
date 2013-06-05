package org.cdahmedeh.orgapp.pers;

import java.io.File;

import org.cdahmedeh.orgapp.types.container.DataContainer;

public interface PersistanceManagerInterface {

	public abstract DataContainer loadDataContainer(File file);

	public abstract void saveDataContainer(File file, DataContainer dataContainer);

}