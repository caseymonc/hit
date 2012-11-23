package model.persistence.DataObjects;

public class ProductGroupDO extends DataObject {
	
	/** The id of the product container that this product group is in*/
	private long containerId;
	
	/** The name of the product group*/
	private String name;
	
	/** The three month supply of the product group*/
	private float threeMonthSupplyVal;
	
	private String threeMonthSupplyUnit;
	
	public ProductGroupDO(long id, String name, long containerId, 
			float threeMonthSupplyVal, String threeMonthSupplyUnit) {
		setId(id);
		this.name = name;
		this.containerId = containerId;
		this.threeMonthSupplyVal = threeMonthSupplyVal;
		this.threeMonthSupplyUnit = threeMonthSupplyUnit;
	}
	
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
	 * set the three month supply value
	 * @param threeMonthSupplyVal
	 */
	public void setThreeMonthSupplyVal(float threeMonthSupplyVal) {
		this.threeMonthSupplyVal = threeMonthSupplyVal;
	}
	
	/**
	 * get the three month supply value
	 * @return
	 */
	public float getThreeMonthSupplyVal() {
		return threeMonthSupplyVal;
	}
	
	/**
	 * set the three month supply unit
	 * @param threeMonthSupplyUnit
	 */
	public void setThreeMonthSupplyUnit(String threeMonthSupplyValUnit) {
		this.threeMonthSupplyUnit = threeMonthSupplyUnit;
	}
	
	/**
	 * get the three month supply unit
	 * @return
	 */
	public String getThreeMonthSupplyUnit() {
		return threeMonthSupplyUnit;
	}
}
