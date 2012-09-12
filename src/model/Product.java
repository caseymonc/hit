package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/** Product
 * A bar-coded product that can be stored in a Storage Unit.
 * Example Products are:
 * Town House Light Buttery Crackers, 13 oz, Barcode 030100169079
 * Colgate Toothpaste-Cavity Protection, 6.40 oz, Barcode 035000509000
 * Alcon Lens Drops, 0.17 fl oz, Barcode 300650192057
 * 
 * Singleton
 * 
 * @Constraint A Product may be in any number of Storage Units. 
 * However, a Product may not be in multiple different Product Containers 
 * within the same Storage Unit at the same time. 
 * That is, a Product may appear at most once in a particular Storage Unit.
 */

public class Product implements PersistentItem{
	private static HashMap<String,Product> singletons;
	
	/** Textual description of the Product.
	 * @Constraint Must be non-empty.*/
	private String description;
	
	/** The date this Product was added to the system.
	 * @Constraint Equal to the earliest Entry Date for any
	 * Item of this Product.*/
	private Date creationDate;
	
	/** Manufacturer's barcode for the Product.
	 * @Constraint Must be non-empty.*/
	private String barCode;
	
	/** The Product's shelf life, measured in months. A value
	 * Must be non-negative.*/
	private int shelfLife;
	
	/** The number of this Product required for a 3-month supply.
	 * A value of zero means "unspecified".*/
	private int threeMonthSupply;
	
	/** The size of the Product. For example, "13 oz", "5 lbs", "0.17 fl oz".
	 */
	private Size size;
	
	/** Product Containers that contain this Product.
	 * @Constraint At most one Product Container in a 
	 * Storage Unit may contain a particular
	 * Product.*/
	private Set<ProductContainer> containers;
	
	/**
	 * @param description - a String description of the Product
	 * @param barCode - the bar code that represents this Product
	 * @param shelfLife - the time in seconds that this Product lasts
	 * @param threeMonthSupply - how much of the Product that you need for a
	 * 							 period of 3 months
	 * @param size - The size of one Item of this Product
	 */
	private Product(String description, String barCode, int shelfLife,
								int threeMonthSupply, Size size){
		this.setDescription(description);
		this.setBarCode(barCode);
		this.setShelfLife(shelfLife);
		this.setThreeMonthSupply(threeMonthSupply);
		this.setSize(size);
		this.setCreationDate(new Date());
		
	}
	
	/**
	 * 
	 * @param description
	 * @param barCode
	 * @param shelfLife
	 * @param threeMonthSupply
	 * @param size
	 * @return a singleton instanceof the Product
	 */
	public static Product getSingleton(String description, String barCode, int shelfLife,
														Size threeMonthSupply, Size size){
		return null;
	}
	
	/**
	 * Delete the Product from the system
	 * @param store
	 * 
	 * @Constraint A Product may be deleted from the system only if there are no 
	 * Items of the Product remaining in the system.
	 */
	public void delete(PersistentStore store){
		
	}
	
	
	/**
	 * Add an item to the parents set
	 * @param parent the ProductContainer to add to the parents set
	 */
	public void addProductContainer(ProductContainer parent){
		
	}
	
	public String sqlCreateStatement() {
		String query = "CREATE TABLE products(" +
				"product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"description TEXT," +
				"barCode TEXT," +
				"creationDate DATETIME," +
				"shelfLife INTEGER," +
				"threeMonthSupply TEXT," +
				"size TEXT" + 
				");";
		return query;
	}
	
	
	/*
	 * Getters and Setters
	 */
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	public int getShelfLife() {
		return shelfLife;
	}

	public void setThreeMonthSupply(int threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}

	public int getThreeMonthSupply() {
		return threeMonthSupply;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Size getSize() {
		return size;
	}

	public void setContainers(Set<ProductContainer> containers) {
		this.containers = containers;
	}

	public Set<ProductContainer> getContainers() {
		return containers;
	}
	
}
