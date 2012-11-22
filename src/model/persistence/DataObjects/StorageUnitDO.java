package model.persistence.DataObjects;

import model.entities.StorageUnit;

public class StorageUnitDO extends DataObject {
	/** The name of the storage unit*/
	private String name;

	public StorageUnitDO(String name) {
		this.name = name;
	}
	
	/** 
	 * Get the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Converts a StrageUnitDO to an actual storageUnit
	 * @return 
	 */
	public StorageUnit toStorageUnit(){
		return new StorageUnit(name);
	}
}
