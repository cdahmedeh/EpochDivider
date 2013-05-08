package org.cdahmedeh.orgapp.pers;

import org.cdahmedeh.orgapp.types.container.DataContainer;

public interface PersistanceManagerInterface {

	public abstract DataContainer loadDataContainer();

	public abstract void saveDataContainer(DataContainer dataContainer);

}