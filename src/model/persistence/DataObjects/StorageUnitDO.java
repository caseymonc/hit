package model.persistence.DataObjects;

public class StorageUnitDO extends DataObject {
	/** The name of the storage unit*/
	private String name;

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
