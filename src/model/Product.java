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
		this.description = description;
		this.barCode = barCode;
		this.shelfLife = shelfLife;
		this.threeMonthSupply = threeMonthSupply;
		this.size = size;
		this.creationDate = new Date();
		
	}
	
	/**
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
	
	/** Checks to see if the product can be deleted.
	 * 
	 * @param store - The core model object
	 * @return true if there are no items of the product remaining in the system.
	 * Otherwise, return false.
	 */
	public boolean canDelete(PersistentStore store){
		return true;
	}
	
	/**
	 * Delete the Product from the system
	 * @param store - The core model object
	 */
	public void delete(PersistentStore store){
		
	}
	
	/** Checks to see if the new information being used to edit a product is valid
	 * 
	 * @param description - The new description of the product
	 * @param shelfLife - The new shelflife of the product
	 * @param threeMonthSupply - The new threeMonthSupply of the product
	 * @param size - The new size of the product
	 * @return true if all of the parameters are valid.  Otherwise return false.
	 */
	public boolean canEdit(String description, int shelfLife, int threeMonthSupply, Size size){
		return true;
	}
	
	/** Edits the product
	 * 
	 * @param description - The new description of the product
	 * @param shelfLife - The new shelflife of the product
	 * @param threeMonthSupply - The new threeMonthSupply of the product
	 * @param size - The new size of the product
	 */
	public void edit(String description, int shelfLife, int threeMonthSupply, Size size){
		
	}
	
	/**
	 * Add a container to the product's set of containers
	 * @param container - The ProductContainer to add to the set of containers
	 */
	public void addProductContainer(ProductContainer container){
		
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

	// Getters and Setters
	
	/** set the description of the product
	 * 
	 * @param description - the description of the product
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** Get the description of the product
	 * 
	 * @return the description of the product
	 */
	public String getDescription() {
		return description;
	}

	/** set the description of the product
	 * 
	 * @param description - the description of the product
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/** Get the creation date of the product
	 * 
	 * @return the creation date of the product
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/** Get the barcode of the product
	 * 
	 * @return the barcode of the product
	 */
	public String getBarCode() {
		return barCode;
	}

	/** set the shelf life of the product
	 * 
	 * @param shelf life - the shelf life of the product
	 */
	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	/** Get the shelf life of the product
	 * 
	 * @return the shelf life of the product
	 */
	public int getShelfLife() {
		return shelfLife;
	}
	
	/** set the three month supply of the product
	 * 
	 * @param three month supply - the three month supply of the product
	 */
	public void setThreeMonthSupply(int threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}

	/** Get the three month supply of the product
	 * 
	 * @return the three month supply of the product
	 */
	public int getThreeMonthSupply() {
		return threeMonthSupply;
	}

	/** set the size of the product
	 * 
	 * @param size - the size of the product
	 */
	public void setSize(Size size) {
		this.size = size;
	}

	/** Get the size of the product
	 * 
	 * @return the size of the product
	 */
	public Size getSize() {
		return size;
	}

	/** Get the containers of the product
	 * 
	 * @return the containers of the product
	 */
	public Set<ProductContainer> getContainers() {
		return containers;
	}
}