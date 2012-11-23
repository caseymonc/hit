package model.persistence.DataObjects;

import model.entities.StorageUnit;

public class StorageUnitDO extends DataObject {
	/** The name of the storage unit*/
	private String name;

	public StorageUnitDO(long id, String name) {
		setId(id);
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
}
