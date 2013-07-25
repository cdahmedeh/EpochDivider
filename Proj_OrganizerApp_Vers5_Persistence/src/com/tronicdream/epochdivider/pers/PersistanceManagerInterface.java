package com.tronicdream.epochdivider.pers;

import java.io.File;

import com.tronicdream.epochdivider.types.container.DataContainer;

public interface PersistanceManagerInterface {

	public abstract DataContainer loadDataContainer(File file);

	public abstract void saveDataContainer(File file, DataContainer dataContainer);

}