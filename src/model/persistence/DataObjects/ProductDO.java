package model.persistence.DataObjects;

public class ProductDO extends DataObject {
	
	/** The description of the Product*/
	private String description;
	
	/** The String representation of the creation date of the product */
	private String creationDate;
	
	/** The String representation of the barcode of the product*/
	private String barCode;
	
	/** The shelf life of the product*/
	private int shelfLife;
	
	/** The three month supply of the product*/
	private int threeMonthSupply;
	
	/** The size of the product as a JSON String*/
	private String size;

	
	/**
	 * Set the description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** 
	 * Get the description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * set the creation date
	 * @param creationDate
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Get the creation date
	 * @return creation date
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * set the barcode
	 * @param barCode
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/** 
	 * get the barcode
	 * @return
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * set the shelf life
	 * @param shelfLife
	 */
	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	/**
	 * get the shelf life
	 * @return
	 */
	public int getShelfLife() {
		return shelfLife;
	}

	/**
	 * set the three month supply
	 * @param threeMonthSupply
	 */
	public void setThreeMonthSupply(int threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}

	/**
	 * get the three month supply
	 * @return
	 */
	public int getThreeMonthSupply() {
		return threeMonthSupply;
	}

	/**
	 * Set the size
	 * @param size
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * get the size
	 * @return
	 */
	public String getSize() {
		return size;
	}
}
