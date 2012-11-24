package model.persistence.DataObjects;

import java.util.Date;

public class ItemDO extends DataObject {

	/** String representation of the bar code*/
	private String barCode;
	
	/** String representation of the entry date*/
	private String entryDate;
	
	/** String representation of the exit date*/
	private String exitDate;
	
	/** boolean representation of removed*/
	private boolean removed;
	
	/** String representation of the expiration date*/
	private String expirationDate;
	
	/** Reference to the Product of this Item*/
	private long productId;
	
	/** Reference to the ProductContainer of this Item*/
	private long containerId;

	public ItemDO(long id, String barCode, String entryDate, String exitDate, 
			String expirationDate, long productId, long containerId){
		setId(id);
		this.barCode = barCode;
		this.entryDate = entryDate;
		this.exitDate = exitDate;
		this.expirationDate = expirationDate;
		this.productId = productId;
		this.containerId = containerId;
	}
	
	/**
	 * Set the String Representation of the barcode
	 * @param barCode The barcode as a string
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * Get the String Representation of the barcode
	 * @return The barcode as a String
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * Set the String Representation of the entry date
	 * @param entryDate get the entry date as a String
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * Get the String Representation of the entry date
	 * @return The entry date as a String
	 */
	public String getEntryDate() {
		return entryDate;
	}

	/**
	 * Set the String Representation of the exit date
	 * @param exitDate The exit date as a String
	 */
	public void setExitDate(String exitDate) {
		this.exitDate = exitDate;
	}

	/**
	 * Get the String Representation of the exit date
	 * @return The exit date as a String
	 */
	public String getExitDate() {
		return exitDate;
	}

	/**
	 * Set whether the Item has been removed
	 * @param removed True if the item has been removed
	 * @param removed False if the item has not been removed
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	/**
	 * Get whether the Item has been removed
	 * @return True if the item has been removed
	 * @return False if the item has not been removed
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * Set the String Representation of the expiration date
	 * @param expirationDate the String Representation of the expiration date
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * Get the String Representation of the expiration date
	 * @return the String Representation of the expiration date
	 */
	public String getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Set the id for the product of this item
	 * @param productId the id for the product of this Item
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * Get the id for the product of this item
	 * @return the id for the product of this item
	 */
	public long getProductId() {
		return productId;
	}

	/**
	 * Set the id for the container of this item
	 * @param containerId the id for the container for this item
	 */
	public void setContainerId(long containerId) {
		this.containerId = containerId;
	}

	/**
	 * Get the id for the container of this item
	 * @return the id for the container of this item
	 */
	public long getContainerId() {
		return containerId;
	}
	
}
