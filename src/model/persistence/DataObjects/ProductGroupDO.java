package model.persistence.DataObjects;

public class ProductGroupDO extends DataObject {
	
	/** The id of the product container that this product group is in*/
	private long containerId;
	
	/** The name of the product group*/
	private String name;
	
	/** The three month supply of the producf group*/
	private String threeMonthSupply;
	
	/**
	 * Set the container id
	 * @param storageUnitId
	 */
	public void setContainerId(long storageUnitId) {
		this.containerId = storageUnitId;
	}
	
	/**
	 * get the container id
	 * @return
	 */
	public long getContainerId() {
		return containerId;
	}
	
	/**
	 * set the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * get the name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * set the three month supply
	 * @param threeMonthSupply
	 */
	public void setThreeMonthSupply(String threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}
	
	/**
	 * get the three month supply
	 * @return
	 */
	public String getThreeMonthSupply() {
		return threeMonthSupply;
	}
}
