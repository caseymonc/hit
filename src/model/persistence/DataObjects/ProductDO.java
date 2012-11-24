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
	
	/** The size value of the product */
	private float sizeVal;

	/** The size unit of the product */
	private String sizeUnit;
	
	public ProductDO(long id, String description, String creationDate, String barCode, 
			int shelfLife, int threeMonthSupply, float sizeVal, String sizeUnit) {
		setId(id);
		this.description = description;
		this.creationDate = creationDate;
		this.barCode = barCode;
		this.shelfLife = shelfLife;
		this.threeMonthSupply = threeMonthSupply;
		this.sizeVal = sizeVal;
		this.sizeUnit = sizeUnit;
	}
	
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
	 * Set the size unit
	 * @param size
	 */
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	/**
	 * get the size unit
	 * @return size unit
	 */
	public String getSizeUnit() {
		return sizeUnit;
	}
	
	/**
	 * Set the size value
	 * @param size value
	 */
	public void setSizeVal(float sizeVal) {
		this.sizeVal = sizeVal;
	}

	/**
	 * get the size value
	 * @return the size value
	 */
	public float getSizeVal() {
		return sizeVal;
	}
}
